package knu.dong.onedayonebaek.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import knu.dong.onedayonebaek.dto.CommitInfo
import knu.dong.onedayonebaek.dto.UserDto
import knu.dong.onedayonebaek.exception.response.BadRequestResponse
import knu.dong.onedayonebaek.service.CommitService
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth


@Validated
@RestController
@RequestMapping("/commits")
class CommitController(private val commitService: CommitService) {

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
    fun getCommits(
        @Schema(
            description = "커밋 목록을 조회하고 싶은 년도와 월",
            required = true,
            example = "2024-05",
            type = "String",
            format = "YYYY-MM"
        )
        yearMonth: YearMonth,
        authentication: Authentication
    ): List<CommitInfo> {
        val userDto = authentication.principal as UserDto

        return commitService.getCommits(userDto.loginId, yearMonth)
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
    fun getCommitCountOfDay(
        @Schema(
            description = "커밋 개수를 조회하고 싶은 날짜",
            required = true,
            example = "2024-05-25"
        )
        date: LocalDate,
        authentication: Authentication
    ): Int {
        val userDto = authentication.principal as UserDto

        return commitService.getCommits(userDto.loginId, date).size
    }
}