package knu.dong.onedayonebaek.holiday.repository

import knu.dong.onedayonebaek.holiday.domain.Holiday
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface HolidayRepository: JpaRepository<Holiday, Long> {

    fun findByDate(date: LocalDate): Optional<Holiday>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Holiday>
    fun existsByDate(date: LocalDate): Boolean
    fun deleteByDateIn(date: List<LocalDate>)
}