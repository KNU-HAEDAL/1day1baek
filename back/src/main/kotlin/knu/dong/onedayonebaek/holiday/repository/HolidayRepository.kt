package knu.dong.onedayonebaek.holiday.repository

import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.holiday.domain.Holiday
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface HolidayRepository: JpaRepository<Holiday, Long> {

    fun findAllByGroupAndDateBetween(group: Group, start: LocalDate, end: LocalDate): List<Holiday>
    fun findAllByGroupAndDateBetweenOrderByDate(group: Group, start: LocalDate, end: LocalDate): List<Holiday>
    fun existsByGroupAndDate(group: Group, date: LocalDate): Boolean
    fun deleteByGroupAndDateIn(group: Group, date: List<LocalDate>)
}