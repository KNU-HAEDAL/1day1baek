package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.group.domain.Group

data class GroupOfListDto(
    @Schema(description = "그룹 ID", nullable = false, example = "1")
    val id: Long,

    @Schema(description = "스터디 그룹명", nullable = false, example = "해달 파이팅")
    val name: String,

    @Schema(description = "비밀 그룹 여부", nullable = false, example = "false")
    val isPrivate: Boolean,

    @Schema(description = "해당 그룹에 속해있는지 여부", nullable = false, example = "false")
    val isMember: Boolean
)

fun Group.toGroupOfListDto(isMember: Boolean) = GroupOfListDto(id!!, name, isPrivate, isMember)