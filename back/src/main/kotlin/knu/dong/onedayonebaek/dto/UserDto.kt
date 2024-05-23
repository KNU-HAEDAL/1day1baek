package knu.dong.onedayonebaek.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.domain.User

data class UserDto(
    @Schema(description = "github login ID", nullable = false, example = "gidskql6671")
    val loginId: String,

    @Schema(description = "github user name", nullable = false, example = "Kim DongHwan")
    val name: String,

    @Schema(description = "github profile url", nullable = false, example = "https://avatars.githubusercontent.com/u/23000498?v=4")
    val profileUrl: String
)

fun UserDto.toEntity() = User(loginId, name, profileUrl)
fun User.fromEntity() = UserDto(loginId, name, profileUrl)