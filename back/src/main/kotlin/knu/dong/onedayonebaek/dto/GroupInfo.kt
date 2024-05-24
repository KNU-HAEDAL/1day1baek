package knu.dong.onedayonebaek.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.domain.Group

data class GroupInfo(
    @Schema(description = "그룹 ID", nullable = false, example = "1")
    val id: Long,

    @Schema(description = "스터디 그룹명", nullable = false, example = "해달 파이팅")
    val name: String,

    @Schema(description = "비밀 그룹 여부", nullable = false, example = "false")
    val isPrivate: Boolean,

    @Schema(description = "입장 코드", example = "b3dad41qSd3rYT1")
    val inviteCode: String? = null
)

fun Group.toGroupInfo() = GroupInfo(id!!, name, isPrivate, inviteCode)