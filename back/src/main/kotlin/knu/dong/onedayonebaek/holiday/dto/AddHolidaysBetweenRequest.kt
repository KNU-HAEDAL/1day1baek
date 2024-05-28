package knu.dong.onedayonebaek.holiday.dto

import java.time.LocalDate

data class AddHolidaysBetweenRequest(val start: LocalDate, val end: LocalDate)