package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.Problem
import knu.dong.onedayonebaek.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate


interface ProblemRepository: JpaRepository<Problem, Long> {
    fun findAllBySolvedDateBetweenAndUser(start: LocalDate, end: LocalDate, user: User): List<Problem>
    fun findAllBySolvedDateAndUser(date: LocalDate, user: User): List<Problem>
}