package knu.dong.onedayonebaek.holiday.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class AddHolidaysRequest(
    @Schema(description = "추가할 휴일 목록")
    val holidays: List<LocalDate> = arrayListOf()
)