package knu.dong.onedayonebaek.holiday.repository

import knu.dong.onedayonebaek.holiday.domain.Holiday
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface HolidayRepository: JpaRepository<Holiday, Long> {

    fun existsByDate(date: LocalDate): Boolean

}