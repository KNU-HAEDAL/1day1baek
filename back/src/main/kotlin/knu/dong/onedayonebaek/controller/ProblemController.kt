package knu.dong.onedayonebaek.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.ProblemDto
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
        @Schema(
            description = "해결한 문제 목록을 조회하고 싶은 년도와 월",
            required = true,
            example = "2024-05",
            type = "String",
            format = "YYYY-MM"
        )
        yearMonth: YearMonth,
        authentication: Authentication
    ): List<ProblemDto> {
        val user = authentication.principal as User

        return problemService.getProblems(user, yearMonth)
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