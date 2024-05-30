package knu.dong.onedayonebaek.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.user.domain.User

data class UserDto(
    @Schema(description = "github login ID", nullable = false, example = "gidskql6671")
    val loginId: String,

    @Schema(description = "github user name", nullable = false, example = "Kim DongHwan")
    val name: String,

    @Schema(description = "github profile url", nullable = false, example = "https://avatars.githubusercontent.com/u/23000498?v=4")
    val profileUrl: String,

    @Schema(description = "User ID", nullable = false, example = "1")
    val id: Long? = null
)

fun UserDto.toEntity() = User(loginId = loginId, name = name, profileUrl = profileUrl)
fun User.toUserDto() = UserDto(loginId = loginId, name = name, profileUrl = profileUrl, id = id)