package knu.dong.onedayonebaek.holiday.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.common.exception.InvalidReqParamException
import knu.dong.onedayonebaek.holiday.dto.AddHolidaysBetweenRequest
import knu.dong.onedayonebaek.holiday.dto.DateUnitForHoliday
import knu.dong.onedayonebaek.holiday.dto.HolidaysRequest
import knu.dong.onedayonebaek.holiday.service.HolidayService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth

@RestController
@RequestMapping("/holidays")
@Tag(name = "Holiday APIs", description = "휴일과 관련된 APIs")
class HolidayController(private val holidayService: HolidayService) {

    @Operation(
        summary = "휴일 조회 API",
        description = "특정 범위의 요일에 해당되는 휴일들을 조회한다."
    )
    @ApiResponses(ApiResponse(responseCode = "200", description = "휴일 목록"))
    @GetMapping
    fun getHolidays(
        @Schema(description = "조회 단위", required = true, implementation = DateUnitForHoliday::class)
        type: DateUnitForHoliday,

        @Schema(description = "type=month - 특정 달의 휴일 목록을 조회", example = "2024-05",
            type = "String",
            format = "YYYY-MM")
        yearMonth: YearMonth?,

        @Schema(description = "type=range - 특정 범위의 날짜 동안의 휴일 목록을 조회(시작 날짜)", example = "2024-05-28")
        startDate: LocalDate?,

        @Schema(description = "type=range - 특정 범위의 날짜 동안의 휴일 목록을 조회(종료 날짜)", example = "2024-05-30")
        endDate: LocalDate?
    ): List<LocalDate> {

        val (start, end) = when (type) {
            DateUnitForHoliday.MONTH -> {
                yearMonth ?: throw InvalidReqParamException("yearMonth 필드가 비어있습니다.")

                Pair(yearMonth.atDay(1), yearMonth.atEndOfMonth())
            }
            else -> {
                Pair(
                    startDate ?: throw InvalidReqParamException("startDate 필드가 비어있습니다."),
                    endDate ?: throw InvalidReqParamException("endDate 필드가 비어있습니다.")
                )
            }
        }

        return holidayService.getHolidaysBetweenDate(start, end)
    }

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