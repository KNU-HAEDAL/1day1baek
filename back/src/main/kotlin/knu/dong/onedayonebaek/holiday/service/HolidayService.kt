package knu.dong.onedayonebaek.holiday.service

import knu.dong.onedayonebaek.common.exception.ForbiddenException
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.containgroup.repository.ContainGroupRepository
import knu.dong.onedayonebaek.group.repository.GroupRepository
import knu.dong.onedayonebaek.holiday.domain.Holiday
import knu.dong.onedayonebaek.holiday.repository.HolidayRepository
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class HolidayService(
    private val holidayRepository: HolidayRepository,
    private val groupRepository: GroupRepository,
    private val containGroupRepository: ContainGroupRepository
) {

    @Transactional
    fun addHolidays(groupId: Long, user: User, dates: List<LocalDate>) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException(code = "not_found_group", message = "존재하지 않는 그룹입니다.") }

        if (group.owner.id != user.id) {
            throw ForbiddenException()
        }

        val holidays: List<Holiday> = dates
            .stream()
            .filter { date -> !holidayRepository.existsByGroupAndDate(group, date) }
            .map { Holiday(group = group, date = it) }
            .toList()

        holidayRepository.saveAll(holidays)
    }

    @Transactional
    fun removeHolidays(groupId: Long, user: User, dates: List<LocalDate>) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException(code = "not_found_group", message = "존재하지 않는 그룹입니다.") }

        if (group.owner.id != user.id) {
            throw ForbiddenException()
        }

        holidayRepository.deleteByGroupAndDateIn(group, dates)
    }

    fun getHolidaysBetweenDate(groupId: Long, user: User, start: LocalDate, end: LocalDate): List<LocalDate> {
        val group = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException(code = "not_found_group", message = "존재하지 않는 그룹입니다.") }

        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException()
        }

        return holidayRepository
            .findAllByGroupAndDateBetween(group, start, end)
            .stream()
            .map(Holiday::date)
            .toList()
    }
}