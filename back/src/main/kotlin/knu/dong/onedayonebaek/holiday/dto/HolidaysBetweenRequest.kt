package knu.dong.onedayonebaek.holiday.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "특정 범위를 휴일로 지정하거나 제거하는 API의 Request Body")
data class HolidaysBetweenRequest(
    @Schema(description = "휴일로 지정하거나 제거할 범위의 시작 날짜")
    val startDate: LocalDate,

    @Schema(description = "휴일로 지정하거나 제거할 범위의 종료 날짜")
    val endDate: LocalDate
)