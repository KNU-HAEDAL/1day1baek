package knu.dong.onedayonebaek.problem.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.github.dto.CommitWebhookRequest
import knu.dong.onedayonebaek.problem.domain.Problem
import knu.dong.onedayonebaek.problem.dto.ProblemDto
import knu.dong.onedayonebaek.problem.dto.toProblemDto
import knu.dong.onedayonebaek.problem.repository.ProblemRepository
import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth
import java.util.regex.Pattern
import java.util.stream.Collectors

private val myLogger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val userRepository: UserRepository
) {
    private val pattern = Pattern.compile("^\\[(?<rank>.*)] Title: (?<title>.*), Time:.*")

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

    @Transactional
    fun addProblems(user: User, commitRequests: List<CommitWebhookRequest.Commit>) {
        val commits = commitRequests
            .stream()
            .filter { !problemRepository.existsByCommitUrl(it.url) }
            .filter { pattern.matcher(it.message).matches() }
            .map {
                var rank = ""
                var title = ""

                val matcher = pattern.matcher(it.message)
                if (matcher.matches()) {
                    rank = matcher.group("rank")
                    title = matcher.group("title")
                }

                Problem(
                    user = user,
                    solvedDate = it.timestamp.plusHours(9).toLocalDate(),
                    commitUrl = it.url,
                    title = title,
                    problemRank = rank
                )
            }
            .toList()

        problemRepository.saveAll(commits)

    }
}