package knu.dong.onedayonebaek.holiday.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "특정 요일을 휴일로 지정하는 API의 Request Body")
data class AddHolidaysUsingDayOfWeekRequest(
    @Schema(description = "휴일로 지정할 요일 목록")
    val dayOfWeeks: List<DayOfWeek>
)