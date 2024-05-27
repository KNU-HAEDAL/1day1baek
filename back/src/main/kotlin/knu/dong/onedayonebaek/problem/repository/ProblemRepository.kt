package knu.dong.onedayonebaek.problem.repository

import knu.dong.onedayonebaek.problem.domain.Problem
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate


interface ProblemRepository: JpaRepository<Problem, Long> {
    fun findAllBySolvedDateBetweenAndUser(start: LocalDate, end: LocalDate, user: User): List<Problem>
    @EntityGraph(attributePaths = ["user"])
    fun findAllBySolvedDateBetweenAndUserIsIn(start: LocalDate, end: LocalDate, users: List<User>): List<Problem>
    fun findAllBySolvedDateAndUser(date: LocalDate, user: User): List<Problem>
    @EntityGraph(attributePaths = ["user"])
    fun findAllBySolvedDateAndUserIsIn(date: LocalDate, users: List<User>): List<Problem>

}