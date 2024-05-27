package knu.dong.onedayonebaek.problem.dto

data class ProblemsOfUser(val problems: MutableList<ProblemDto>, val username: String, val userId: Long)