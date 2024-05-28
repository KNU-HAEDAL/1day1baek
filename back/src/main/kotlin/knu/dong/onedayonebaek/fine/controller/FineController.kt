package knu.dong.onedayonebaek.fine.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.common.dto.DateUnit
import knu.dong.onedayonebaek.common.exception.ForbiddenException
import knu.dong.onedayonebaek.common.exception.InvalidReqParamException
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.common.exception.response.ForbiddenResponse
import knu.dong.onedayonebaek.common.exception.response.NotFoundResponse
import knu.dong.onedayonebaek.containgroup.service.ContainGroupService
import knu.dong.onedayonebaek.fine.dto.GetFinesDto
import knu.dong.onedayonebaek.fine.service.FineService
import knu.dong.onedayonebaek.group.repository.GroupRepository
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.YearMonth


@RestController
@RequestMapping("/groups/{groupId}/fine")
@Tag(name = "Fine APIs", description = "그룹의 벌금과 관련된 APIs")
class FineController(
    private val fineService: FineService,
    private val containGroupService: ContainGroupService,
    private val groupRepository: GroupRepository
) {

    @Operation(
        summary = "스터디 그룹원들의 벌금 목록 조회",
        description = "스터디 그룹원들의 벌금을 특정 일자 단위로 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "스터디 그룹원들이 해결한 벌금 목록"),
        ApiResponse(
            responseCode = "403", description = "속해있지 않은 비밀 스터디 그룹",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class))],
        )
    )
    @GetMapping
    fun getFines(
        @PathVariable groupId: Long,

        @Schema(description = "조회 단위", required = true, implementation = DateUnit::class)
        type: DateUnit,

        @Schema(description = "type=DAY - 특정 일의 벌금 목록을 조회", example = "2024-05-28")
        date: LocalDate?,

        @Schema(description = "type=MONTH - 특정 달의 벌금 목록을 조회", example = "2024-05",
            type = "String", format = "YYYY-MM")
        yearMonth: YearMonth?,

        @Schema(description = "type=RANGE - 특정 범위의 날짜 동안의 벌금 목록을 조회(시작 날짜)", example = "2024-05-28")
        startDate: LocalDate?,

        @Schema(description = "type=RANGE - 특정 범위의 날짜 동안의 벌금 목록을 조회(종료 날짜)", example = "2024-05-30")
        endDate: LocalDate?,

        authentication: Authentication
    ): List<GetFinesDto> {
        val user = authentication.principal as User
        val group = groupRepository.findById(groupId)
            .orElseThrow{ NotFoundException(code = "not_found_group", message = "존재하지 않는 그룹") }

        if (group.isPrivate && !containGroupService.isMemberOfGroup(group, user)) {
            throw ForbiddenException(message = "비밀 그룹에 속해있지 않습니다.")
        }

        val (start, end) = when (type) {
            DateUnit.DAY -> {
                date ?: throw InvalidReqParamException("date 필드가 비어있습니다.")

                Pair(date, date)
            }
            DateUnit.MONTH -> {
                yearMonth ?: throw InvalidReqParamException("yearMonth 필드가 비어있습니다.")

                Pair(yearMonth.atDay(1), yearMonth.atEndOfMonth())
            }
            else -> {
                Pair(
                    startDate ?: throw InvalidReqParamException("startDate 필드가 비어있습니다."),
                    endDate ?: throw InvalidReqParamException("endDate 필드가 비어있습니다.")
                )
            }
        }

        return fineService.getFines(group, start, end).map { GetFinesDto(it.key, it.value) }
    }
}