package knu.dong.onedayonebaek.problem.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "특정 일의 해결한 문제 개수를 조회하는 API의 Response Body")
data class GetProblemCountOfDayResponse(
    @Schema(description = "해결한 문제 개수", nullable = false)
    val count: Int,

    @Schema(description = "해당 일", nullable = false)
    val date: LocalDate
)
