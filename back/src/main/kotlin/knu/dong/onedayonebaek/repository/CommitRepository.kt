package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.Commit
import knu.dong.onedayonebaek.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate


interface CommitRepository: JpaRepository<Commit, Long> {
    fun findAllByCommitDateBetweenAndUser(start: LocalDate, end: LocalDate, user: User): List<Commit>
}