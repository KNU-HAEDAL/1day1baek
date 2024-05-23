package knu.dong.onedayonebaek.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.domain.Commit
import java.time.LocalDate

data class CommitInfo(
    @Schema(description = "커밋 날짜", nullable = false, example = "2024-05-09")
    val commitDate: LocalDate,

    @Schema(description = "커밋 url", nullable = false, example = "https://github.com/gidskql6671")
    val commitUrl: String,

    @Schema(description = "사용자명", nullable = false, example = "Kim DongHwan")
    val username: String,

    @Schema(description = "문제 제목", nullable = false, example = "아기 상어")
    val problemTitle: String,

    @Schema(description = "문제 난이도", nullable = false, example = "Gold V")
    val problemRank: String
)


fun Commit.toCommitInfo() = CommitInfo(commitDate, commitUrl, user.name, problemTitle, problemRank)