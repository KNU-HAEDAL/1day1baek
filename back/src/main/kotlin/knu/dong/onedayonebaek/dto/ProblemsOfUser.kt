package knu.dong.onedayonebaek.dto

data class ProblemsOfUser(val problems: MutableList<ProblemDto>, val username: String, val userId: Long)