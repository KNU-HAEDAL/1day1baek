package knu.dong.onedayonebaek.group.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import knu.dong.onedayonebaek.common.dto.DateUnit
import knu.dong.onedayonebaek.dto.*
import knu.dong.onedayonebaek.common.exception.InvalidReqParamException
import knu.dong.onedayonebaek.common.exception.response.BadRequestResponse
import knu.dong.onedayonebaek.common.exception.response.ForbiddenResponse
import knu.dong.onedayonebaek.common.exception.response.NotFoundResponse
import knu.dong.onedayonebaek.group.dto.CreateGroupRequest
import knu.dong.onedayonebaek.group.dto.GroupDetailDto
import knu.dong.onedayonebaek.group.dto.JoinGroupRequest
import knu.dong.onedayonebaek.group.dto.JoinGroupWithInviteCodeRequest
import knu.dong.onedayonebaek.group.service.GroupService
import knu.dong.onedayonebaek.problem.dto.ProblemsOfUser
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth


private val myLogger = KotlinLogging.logger {}

@RestController
@RequestMapping("/groups")
class GroupController(
  private val groupService: GroupService
) {
    @Operation(
        summary = "그룹 목록 조회",
        description = "모든 그룹을 조회한다."
    )
    @GetMapping
    fun getGroups() = groupService.getGroups()

    @Operation(
        summary = "스터디 그룹 상세 조회",
        description = "지정된 스터디 그룹을 상세 조회한다."
    )
    @GetMapping("/{groupId}")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "스터디 그룹 상세 정보"),
        ApiResponse(
            responseCode = "403", description = "속해있지 않은 비밀 스터디 그룹에 접근 시도",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))],
        )
    )
    fun getGroupDetail(@PathVariable groupId: Long, authentication: Authentication): GroupDetailDto {
        val user = authentication.principal as User

        return groupService.getGroupDetail(user, groupId)
    }


    @Operation(
        summary = "그룹 생성",
        description = "새로운 그룹을 만든다."
    )
    @PostMapping
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "스터디 그룹 상세 정보"),
        ApiResponse(
            responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = InvalidReqParamException::class))],
        )
    )
    fun createGroup(
        @Validated @RequestBody requestDto: CreateGroupRequest,
        authentication: Authentication
    ): GroupDetailDto {
        val user = authentication.principal as User

        if (requestDto.isPrivate && requestDto.password?.isBlank() != false) {
            throw InvalidReqParamException("비밀 그룹은 비밀번호가 필요합니다.")
        }

        return groupService.createGroup(user, requestDto)
    }

    @Operation(
        summary = "스터디 그룹 참가",
        description = "지정된 스터디 그룹을 참가한다."
    )
    @PostMapping("/{groupId}/join")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "참가한 스터디 그룹의 상세 정보"),
        ApiResponse(
            responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = BadRequestResponse::class))]
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))]
        ),
        ApiResponse(
            responseCode = "409", description = "- 비밀번호 불일치\n- 해당 스터디 그룹에 이미 속해있음",
            content = [Content(
                examples = [
                    ExampleObject(
                        name = "비밀 그룹 비밀번호 불일치",
                        value = "{\"code\": \"incorrect_password\", \"message\":\"비밀번호가 다릅니다.\"}"
                    ), ExampleObject(
                        name = "이미 가입된 그룹",
                        value = "{\"code\": \"already_joined\", \"message\":\"이미 가입된 스터디 그룹입니다.\"}"
                    )
                ],
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )]
        )
    )
    fun joinGroup(
        @PathVariable groupId: Long,
        @Validated @RequestBody requestDto: JoinGroupRequest,
        authentication: Authentication
    ): GroupDetailDto {
        val user = authentication.principal as User

        return groupService.joinGroup(user, groupId, requestDto.password)
    }

    @Operation(
        summary = "스터디 그룹 참가(초대 코드 사용)",
        description = "초대 코드를 사용하여 지정된 스터디 그룹을 참가한다."
    )
    @PostMapping("/invited")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "참가한 스터디 그룹의 상세 정보"),
        ApiResponse(
            responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = BadRequestResponse::class))]
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))]
        ),
        ApiResponse(
            responseCode = "409", description = "- 해당 스터디 그룹에 이미 속해있음",
            content = [Content(
                examples = [
                    ExampleObject(
                        name = "이미 가입된 그룹",
                        value = "{\"code\": \"already_joined\", \"message\":\"이미 가입된 스터디 그룹입니다.\"}"
                    )
                ],
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )]
        )
    )
    fun joinGroupWithInviteCode(
        @Validated @RequestBody requestDto: JoinGroupWithInviteCodeRequest,
        authentication: Authentication
    ): GroupDetailDto {
        val user = authentication.principal as User

        return groupService.joinGroupWithInviteCode(user, requestDto.inviteCode)
    }

    @Operation(
        summary = "그룹 나가기",
        description = "해당 그룹에서 나간다. 그룹장은 나갈 수 없다."
    )
    @PostMapping("/{groupId}/leave")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "스터디 그룹 상세 정보"),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))]
        ),
        ApiResponse(
            responseCode = "409", description = "- 사용자가 그룹 장임\n- 해당 스터디 그룹에 속해있지 않음",
            content = [Content(
                examples = [
                    ExampleObject(
                        name = "비밀 그룹 비밀번호 불일치",
                        value = "{\"code\": \"not_allowed_leave_group_owner\", \"message\":\"그룹 장은 나갈 수 없습니다.\"}"
                    ), ExampleObject(
                        name = "이미 가입된 그룹",
                        value = "{\"code\": \"already_leaved\", \"message\":\"해당 스터디 그룹에 속해있지 않습니다.\"}"
                    )
                ],
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )]
        )
    )
    fun leaveGroup(@PathVariable groupId: Long, authentication: Authentication) {
        val user = authentication.principal as User

        groupService.leaveGroup(user, groupId)
    }

    @Operation(
        summary = "스터디 그룹원들의 해결한 문제 목록 조회",
        description = "스터디 그룹원들이 해결한 문제를 특정 일자 단위로 조회한다."
    )
    @GetMapping("/{groupId}/problems")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "스터디 그룹원들이 해결한 문제 목록"),
        ApiResponse(
            responseCode = "403", description = "속해있지 않은 스터디 그룹",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))],
        )
    )
    fun getProblems(
        @PathVariable groupId: Long,

        @Schema(description = "조회 단위", required = true, implementation = DateUnit::class)
        type: DateUnit,

        @Schema(description = "type=day - 특정 일의 해결한 문제 목록을 조회", example = "2024-05-28")
        date: LocalDate?,

        @Schema(description = "type=month - 특정 달의 해결한 문제 목록을 조회", example = "2024-05",
            type = "String",
            format = "YYYY-MM")
        yearMonth: YearMonth?,

        @Schema(description = "type=range - 특정 범위의 날짜 동안 해결한 문제 목록을 조회(시작 날짜)", example = "2024-05-28")
        startDate: LocalDate?,

        @Schema(description = "type=range - 특정 범위의 날짜 동안 해결한 문제 목록을 조회(종료 날짜)", example = "2024-05-30")
        endDate: LocalDate?,
        authentication: Authentication
    ): List<ProblemsOfUser>{
        val user = authentication.principal as User

        return when (type) {
            DateUnit.DAY -> {
                groupService.getProblems(user, groupId,
                    date ?: throw InvalidReqParamException("date 필드가 비어있습니다.")
                )
            }
            DateUnit.MONTH -> {
                groupService.getProblems(user, groupId,
                    yearMonth ?: throw InvalidReqParamException("yearMonth 필드가 비어있습니다.")
                )
            }
            else -> {
                groupService.getProblems(user, groupId,
                    startDate ?: throw InvalidReqParamException("startDate 필드가 비어있습니다."),
                    endDate ?: throw InvalidReqParamException("endDate 필드가 비어있습니다.")
                )
            }
        }
    }
}