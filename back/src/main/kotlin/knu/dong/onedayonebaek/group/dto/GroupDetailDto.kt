package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.user.dto.UserDto
import knu.dong.onedayonebaek.user.dto.toUserDto
import knu.dong.onedayonebaek.group.domain.Group

@Schema(description = "그룹 생성 응답 DTO")
data class GroupDetailDto(
    @Schema(description = "그룹 ID", nullable = false, example = "1")
    val id: Long,

    @Schema(description = "그룹 이름", required = true, example = "해달 짱")
    val name: String,

    @Schema(description = "비밀 그룹 여부", required = true, example = "false")
    val isPrivate: Boolean,

    val inviteCode: String,

    val users: List<UserDto>,

    val owner: UserDto
)

fun Group.toGroupDetailDto() =
    GroupDetailDto(
        id!!,
        name,
        isPrivate,
        inviteCode,
        users.map { it.user.toUserDto() },
        owner.toUserDto()
    )

