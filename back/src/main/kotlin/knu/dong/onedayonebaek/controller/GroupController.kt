package knu.dong.onedayonebaek.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.CreateGroupRequest
import knu.dong.onedayonebaek.dto.GroupDetailDto
import knu.dong.onedayonebaek.dto.JoinGroupRequest
import knu.dong.onedayonebaek.exception.InvalidReqParamException
import knu.dong.onedayonebaek.exception.response.BadRequestResponse
import knu.dong.onedayonebaek.exception.response.ForbiddenResponse
import knu.dong.onedayonebaek.exception.response.NotFoundResponse
import knu.dong.onedayonebaek.service.GroupService
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


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
        ApiResponse(responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = BadRequestResponse::class))]),
        ApiResponse(responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))]),
        ApiResponse(responseCode = "409", description = "비밀 스터디 그룹 비밀번호 불일치",
            content = [Content(
                examples = [
                    ExampleObject(
                        name = "비밀 그룹 비밀번호 불일치",
                        value = "{\"code\": \"incorrect_password\", \"message\":\"비밀번호가 다릅니다.\"}"
                    ),ExampleObject(
                        name = "이미 가입된 그룹",
                        value = "{\"code\": \"already_joined\", \"message\":\"이미 가입된 스터디 그룹입니다.\"}"
                    )
                ],
                mediaType = MediaType.APPLICATION_JSON_VALUE)])
    )
    fun joinGroup(
        @PathVariable groupId: Long,
        @Validated @RequestBody requestDto: JoinGroupRequest,
        authentication: Authentication
    ): GroupDetailDto {
        val user = authentication.principal as User

        return groupService.joinGroup(user, groupId, requestDto.password)
    }
}