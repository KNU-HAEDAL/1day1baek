package knu.dong.onedayonebaek.oauth.dto

import knu.dong.onedayonebaek.domain.User

data class UserDto(val loginId: String, val name: String, val profileUrl: String)

fun UserDto.toEntity() = User(loginId, name, profileUrl)
fun User.fromEntity() = UserDto(loginId, name, profileUrl)