package knu.dong.onedayonebaek.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.ProblemDto
import knu.dong.onedayonebaek.dto.toProblemDto
import knu.dong.onedayonebaek.repository.ProblemRepository
import knu.dong.onedayonebaek.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth
import java.util.stream.Collectors

private val myLogger = KotlinLogging.logger {}

@Service
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val userRepository: UserRepository
) {

    fun getProblems(user: User, yearMonth: YearMonth): List<ProblemDto> {
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        return problemRepository
            .findAllBySolvedDateBetweenAndUser(start, end, user)
            .stream()
            .map { it.toProblemDto() }
            .collect(Collectors.toList())
    }

    fun getProblems(user: User, date: LocalDate): List<ProblemDto> {
        return problemRepository
            .findAllBySolvedDateAndUser(date, user)
            .stream()
            .map { it.toProblemDto() }
            .collect(Collectors.toList())
    }

    fun getProblems(user: User, startDate: LocalDate, endDate: LocalDate): List<ProblemDto> {
        return problemRepository
            .findAllBySolvedDateBetweenAndUser(startDate, endDate, user)
            .stream()
            .map { it.toProblemDto() }
            .collect(Collectors.toList())
    }
}