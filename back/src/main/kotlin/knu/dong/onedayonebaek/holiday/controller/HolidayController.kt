package knu.dong.onedayonebaek.holiday.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.holiday.dto.AddHolidaysRequest
import knu.dong.onedayonebaek.holiday.service.HolidayService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/holidays")
@Tag(name = "Holiday APIs", description = "휴일과 관련된 APIs")
class HolidayController(private val holidayService: HolidayService) {

    @Operation(
        hidden = true,
        summary = "휴일 추가 API",
        description = "휴일을 추가한다. (권한 추가를 안해서 사용하지 마십숑)"
    )
    @PostMapping
    fun addHolidays(@RequestBody request: AddHolidaysRequest) {
        holidayService.addHolidays(request.holidays)
    }
}