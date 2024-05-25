package knu.dong.onedayonebaek.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.CommitInfo
import knu.dong.onedayonebaek.dto.toCommitInfo
import knu.dong.onedayonebaek.repository.CommitRepository
import knu.dong.onedayonebaek.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth
import java.util.stream.Collectors

private val myLogger = KotlinLogging.logger {}

@Service
class CommitService(
    private val commitRepository: CommitRepository,
    private val userRepository: UserRepository
) {

    fun getCommits(user: User, yearMonth: YearMonth): List<CommitInfo> {
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        return commitRepository
            .findAllByCommitDateBetweenAndUser(start, end, user)
            .stream()
            .map { it.toCommitInfo() }
            .collect(Collectors.toList())
    }

    fun getCommits(user: User, date: LocalDate): List<CommitInfo> {
        return commitRepository
            .findAllByCommitDateAndUser(date, user)
            .stream()
            .map { it.toCommitInfo() }
            .collect(Collectors.toList())
    }
}