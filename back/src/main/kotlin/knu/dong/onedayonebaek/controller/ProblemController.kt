package knu.dong.onedayonebaek.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.DateUnit
import knu.dong.onedayonebaek.dto.ProblemDto
import knu.dong.onedayonebaek.exception.InvalidReqParamException
import knu.dong.onedayonebaek.exception.response.BadRequestResponse
import knu.dong.onedayonebaek.service.ProblemService
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth


@Validated
@RestController
@RequestMapping("/problems")
@Tag(name = "해결한 문제 APIs", description = "해결한 문제들을 조회하는 APIs")
class ProblemController(private val problemService: ProblemService) {

    @Operation(
        summary = "로그인된 유저가 해결한 문제 목록 조회",
        description = "로그인된 유저가 특정 달에 해결한 문제 목록을 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그인된 유저의 문제 목록 조회"),
        ApiResponse(
            responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = BadRequestResponse::class))],
        )
    )
    @GetMapping
    fun getProblems(
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
    ): List<ProblemDto> {
        val user = authentication.principal as User

        return when (type) {
            DateUnit.DAY -> {
                problemService.getProblems(user,
                    date ?: throw InvalidReqParamException("date 필드가 비어있습니다.")
                )
            }
            DateUnit.MONTH -> {
                problemService.getProblems(user,
                    yearMonth ?: throw InvalidReqParamException("yearMonth 필드가 비어있습니다.")
                )
            }
            else -> {
                problemService.getProblems(user,
                    startDate ?: throw InvalidReqParamException("startDate 필드가 비어있습니다."),
                    endDate ?: throw InvalidReqParamException("endDate 필드가 비어있습니다.")
                )
            }
        }
    }

    @Operation(
        summary = "로그인된 유저의 특정 일자에 해결한 문제 개수 조회",
        description = "로그인된 유저의 특정 일자에 해결한 문제 개수를 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그인된 유저의 특정 일자에 해당하는 해결한 문제 개수"),
        ApiResponse(
            responseCode = "400", description = "잘못된 Request Parameter",
            content = [Content(schema = Schema(implementation = BadRequestResponse::class))],
        )
    )
    @GetMapping("/count")
    fun getProblemCountOfDay(
        @Schema(
            description = "해결한 문제 개수를 조회하고 싶은 날짜",
            required = true,
            example = "2024-05-25"
        )
        date: LocalDate,
        authentication: Authentication
    ): Int {
        val user = authentication.principal as User

        return problemService.getProblems(user, date).size
    }
}