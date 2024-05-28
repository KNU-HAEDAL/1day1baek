package knu.dong.onedayonebaek.fine.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.containgroup.repository.ContainGroupRepository
import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.holiday.domain.Holiday
import knu.dong.onedayonebaek.holiday.repository.HolidayRepository
import knu.dong.onedayonebaek.problem.repository.ProblemRepository
import knu.dong.onedayonebaek.user.dto.UserDto
import knu.dong.onedayonebaek.user.dto.toUserDto
import org.springframework.stereotype.Service
import java.time.LocalDate

private val myLogger = KotlinLogging.logger {}


@Service
class FineService(
    private val problemRepository: ProblemRepository,
    private val containGroupRepository: ContainGroupRepository,
    private val holidayRepository: HolidayRepository
) {

    fun getFines(group: Group, startDate: LocalDate, endDate: LocalDate): Map<UserDto, Int> {
        val users = containGroupRepository.findAllByGroup(group).map { it.user }
        val holidays = holidayRepository.findAllByGroupAndDateBetween(group, startDate, endDate)
        val targetDates = applyHolidays(calculateRange(startDate, endDate), holidays)

        val problemsOfUsers =
            problemRepository
                .findAllBySolvedDateBetweenAndUserIsIn(startDate, endDate, users)
                .groupBy { it.user.id }

        val result = mutableMapOf<UserDto, Int>()
        for (user in users) {
            val userDto = user.toUserDto()

            if (problemsOfUsers.containsKey(user.id)) {
                val solvedDates = problemsOfUsers[user.id]!!
                    .groupingBy { it.solvedDate }.eachCount()

                myLogger.error { solvedDates }
                myLogger.error { targetDates }
                result[userDto] = targetDates.count {
                    if (solvedDates.containsKey(it)) {
                        solvedDates[it]!! < group.goalSolveCount
                    }
                    else {
                        true
                    }
                } * group.fine
            }
            else {
                result[userDto] = targetDates.size * group.fine
            }
        }

        return result
    }

    private fun calculateRange(startDate: LocalDate, endDate: LocalDate) : List<LocalDate> {
        val localDates: MutableList<LocalDate> = ArrayList()

        var date = startDate
        while (!date.isAfter(endDate)) {
            localDates.add(date)
            date = date.plusDays(1)
        }

        return localDates
    }

    private fun applyHolidays(dates: List<LocalDate>, holidays: List<Holiday>) : List<LocalDate> {
        val eraseLocalDates = holidays.map { it.date }.toSet()

        return dates.filter { !eraseLocalDates.contains(it) }
    }
}