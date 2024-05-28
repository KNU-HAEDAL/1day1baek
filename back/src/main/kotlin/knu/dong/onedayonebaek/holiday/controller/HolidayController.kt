package knu.dong.onedayonebaek.holiday.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.holiday.dto.AddHolidaysBetweenRequest
import knu.dong.onedayonebaek.holiday.dto.HolidaysRequest
import knu.dong.onedayonebaek.holiday.service.HolidayService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/holidays")
@Tag(name = "Holiday APIs", description = "휴일과 관련된 APIs")
class HolidayController(private val holidayService: HolidayService) {

    @Operation(
        hidden = true,
        summary = "휴일 추가 API",
        description = "휴일들을 추가한다. (권한 추가를 안해서 사용하지 마십숑)"
    )
    @PostMapping
    fun addHolidays(@RequestBody request: HolidaysRequest) {
        holidayService.addHolidays(request.holidays)
    }

    @Operation(
        hidden = true,
        summary = "휴일 범위로 추가 API",
        description = "특정 범위를 모두 휴일로 추가한다. (권한 추가를 안해서 사용하지 마십숑)"
    )
    @PostMapping("/range")
    fun addHolidaysBetween(@RequestBody request: AddHolidaysBetweenRequest) {
        val start: LocalDate = request.start
        val end: LocalDate = request.end

        val dates: MutableList<LocalDate> = ArrayList()

        var date = start
        while (!date.isAfter(end)) {
            dates.add(date)
            date = date.plusDays(1)
        }

        holidayService.addHolidays(dates)
    }

    @Operation(
        hidden = true,
        summary = "휴일 삭제 API",
        description = "휴일을 제거한다. (권한 추가를 안해서 사용하지 마십숑)"
    )
    @DeleteMapping
    fun removeHolidays(@RequestBody request: HolidaysRequest) {
        holidayService.removeHolidays(request.holidays)
    }
}