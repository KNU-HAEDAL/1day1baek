package knu.dong.onedayonebaek.problem.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.problem.domain.Problem
import java.time.LocalDate

data class ProblemDto(
    @Schema(description = "문제를 해결한 날짜", nullable = false, example = "2024-05-09")
    val solvedDate: LocalDate,

    @Schema(description = "문제 코드가 담긴 커밋 url", nullable = false, example = "https://github.com/gidskql6671")
    val commitUrl: String,

    @Schema(description = "사용자명", nullable = false, example = "Kim DongHwan")
    val username: String,

    @Schema(description = "문제 제목", nullable = false, example = "아기 상어")
    val title: String,

    @Schema(description = "문제 난이도", nullable = false, example = "Gold V")
    val rank: String
)


fun Problem.toProblemDto() = ProblemDto(solvedDate, commitUrl, user.name, title, problemRank)