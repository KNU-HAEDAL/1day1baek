package knu.dong.onedayonebaek.holiday.batch

import knu.dong.onedayonebaek.holiday.service.HolidayService
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class HolidaySynchronizer(private val holidayService: HolidayService): ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        holidayService.sync(LocalDate.of(2024, 1, 1), LocalDate.of(2025, 12, 31))
    }
}