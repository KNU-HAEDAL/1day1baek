package knu.dong.onedayonebaek.holiday.service

import knu.dong.onedayonebaek.holiday.domain.Holiday
import knu.dong.onedayonebaek.holiday.repository.HolidayRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class HolidayService(private val holidayRepository: HolidayRepository) {

    @Transactional
    fun addHolidays(dates: List<LocalDate>) {
        val holidays: List<Holiday> = dates
            .stream()
            .filter { date -> !holidayRepository.existsByDate(date) }
            .map { Holiday(date = it) }
            .toList()

        holidayRepository.saveAll(holidays)
    }

    @Transactional
    fun removeHolidays(dates: List<LocalDate>) {
        holidayRepository.deleteByDateIn(dates)
    }

    @Transactional
    fun sync(start: LocalDate, end: LocalDate) {
        var date = start
        while (!date.isAfter(end)) {
            val dayOfWeek = date.dayOfWeek
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                if (!holidayRepository.existsByDate(date)) {
                    holidayRepository.save(Holiday(date = date))
                }
            }
            date = date.plusDays(1)
        }
    }
}