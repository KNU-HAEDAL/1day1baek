package knu.dong.onedayonebaek.holiday.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.common.exception.InvalidReqParamException
import knu.dong.onedayonebaek.common.exception.response.ForbiddenResponse
import knu.dong.onedayonebaek.common.exception.response.NotFoundResponse
import knu.dong.onedayonebaek.holiday.dto.*
import knu.dong.onedayonebaek.holiday.service.HolidayService
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth

@RestController
@RequestMapping("groups/{groupId}/holidays")
@Tag(name = "Holiday APIs", description = "그룹 휴일과 관련된 APIs")
class HolidayController(private val holidayService: HolidayService) {

    @Operation(
        summary = "휴일 조회 API",
        description = "특정 범위의 요일에 해당되는 휴일들을 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "휴일 목록"),
        ApiResponse(
            responseCode = "403", description = "비밀 그룹에 속해있지 않음",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    @GetMapping
    fun getHolidays(
        @PathVariable groupId: Long,

        @Schema(description = "조회 단위", required = true, implementation = DateUnitForHoliday::class)
        type: DateUnitForHoliday,

        @Schema(description = "type=MONTH - 특정 달의 휴일 목록을 조회", example = "2024-05",
            type = "String",
            format = "YYYY-MM")
        yearMonth: YearMonth?,

        @Schema(description = "type=RANGE - 특정 범위의 날짜 동안의 휴일 목록을 조회(시작 날짜)", example = "2024-05-28")
        startDate: LocalDate?,

        @Schema(description = "type=RANGE - 특정 범위의 날짜 동안의 휴일 목록을 조회(종료 날짜)", example = "2024-05-30")
        endDate: LocalDate?,

        authentication: Authentication
    ): List<LocalDate> {
        val user = authentication.principal as User

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

        return holidayService.getHolidaysBetweenDate(groupId, user, start, end)
    }

    @Operation(
        summary = "휴일 추가 API",
        description = "휴일들을 추가한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "추가 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    @PostMapping
    fun addHolidays(
        @PathVariable groupId: Long,
        @RequestBody request: HolidaysRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        holidayService.addHolidays(groupId, user, request.holidays)
    }

    @Operation(
        summary = "특정 요일을 휴일로 추가하는 API",
        description = "지정된 요일을 휴일로 추가한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "추가 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    @PostMapping("/dayOfWeek")
    fun addHolidaysUsingDayOfWeek(
        @PathVariable groupId: Long,
        @RequestBody requestDto: HolidaysUsingDayOfWeekRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        val holidays = calculateLocalDatesOf(requestDto.dayOfWeeks)

        holidayService.addHolidays(groupId, user, holidays)
    }

    @Operation(
        summary = "휴일 범위로 추가 API",
        description = "특정 범위를 모두 휴일로 추가한다."
    )
    @PostMapping("/range")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "추가 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    fun addHolidaysUsingRange(
        @PathVariable groupId: Long,
        @RequestBody requestDto: HolidaysBetweenRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        val holidays = calculateLocalDateOf(requestDto.startDate, requestDto.endDate)

        holidayService.addHolidays(groupId, user, holidays)
    }

    @Operation(
        summary = "휴일 삭제 API",
        description = "휴일을 제거한다."
    )
    @DeleteMapping
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "삭제 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    fun removeHolidays(
        @PathVariable groupId: Long,
        @RequestBody request: HolidaysRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        holidayService.removeHolidays(groupId, user, request.holidays)
    }

    @Operation(
        summary = "특정 요일을 휴일에서 제거하는 API",
        description = "특정 요일을 휴일에서 제거한다."
    )
    @DeleteMapping("/dayOfWeek")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "삭제 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    fun removeHolidaysUsingDayOfWeek(
        @PathVariable groupId: Long,
        @RequestBody requestDto: HolidaysUsingDayOfWeekRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        val holidays = calculateLocalDatesOf(requestDto.dayOfWeeks)

        holidayService.removeHolidays(groupId, user, holidays)
    }

    @Operation(
        summary = "휴일을 범위로 제거 API",
        description = "특정 범위를 모두 휴일에서 제거한다."
    )
    @DeleteMapping("/range")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "삭제 성공"),
        ApiResponse(
            responseCode = "403", description = "그룹 관리자가 아님",
            content = [Content(schema = Schema(implementation = ForbiddenResponse::class))],
        ),
        ApiResponse(
            responseCode = "404", description = "존재하지 않는 스터디 그룹",
            content = [Content(schema = Schema(implementation = NotFoundResponse::class,))]
        )
    )
    fun removeHolidaysUsingRange(
        @PathVariable groupId: Long,
        @RequestBody requestDto: HolidaysBetweenRequest,
        authentication: Authentication
    ) {
        val user = authentication.principal as User

        val holidays = calculateLocalDateOf(requestDto.startDate, requestDto.endDate)

        holidayService.removeHolidays(groupId, user, holidays)
    }

    private fun calculateLocalDatesOf(dayOfWeeks: List<DayOfWeek>): List<LocalDate> {
        val valueDayOfWeeks = dayOfWeeks.map { it.value }

        val startDate = LocalDate.now()
        val endDate = startDate.plusYears(1)

        val localDates = arrayListOf<LocalDate>()
        var date = startDate
        while (!date.isAfter(endDate)) {
            val dayOfWeek = date.dayOfWeek.value

            if (dayOfWeek in valueDayOfWeeks) {
                localDates.add(date)
            }

            date = date.plusDays(1)
        }

        return localDates
    }

    private fun calculateLocalDateOf(startDate: LocalDate, endDate: LocalDate) : List<LocalDate> {
        val localDates: MutableList<LocalDate> = ArrayList()

        var date = startDate
        while (!date.isAfter(endDate)) {
            localDates.add(date)
            date = date.plusDays(1)
        }

        return localDates
    }
}