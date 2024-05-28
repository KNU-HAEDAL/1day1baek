package knu.dong.onedayonebaek.holiday.dto

import java.time.LocalDate

data class AddHolidaysBetweenRequest(val startDate: LocalDate, val endDate: LocalDate)