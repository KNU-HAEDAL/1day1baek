package knu.dong.onedayonebaek.holiday.service

import knu.dong.onedayonebaek.holiday.domain.Holiday
import knu.dong.onedayonebaek.holiday.repository.HolidayRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
}