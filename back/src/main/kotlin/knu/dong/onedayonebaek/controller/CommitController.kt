package knu.dong.onedayonebaek.controller

import io.github.oshai.kotlinlogging.KotlinLogging
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
import java.time.YearMonth

private val myLogger = KotlinLogging.logger {}

@Validated
@RestController
@RequestMapping("/commits")
class CommitController(private val commitService: CommitService) {

    @Operation(
        summary = "로그인된 유저의 커밋 조회",
        description = "로그인된 유저의 커밋들을 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그인된 유저의 커밋 목록 조회"),
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
}