package knu.dong.onedayonebaek.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.domain.Group
import knu.dong.onedayonebaek.domain.User

@Schema(description = "그룹 생성 요청 DTO")
data class CreateGroupRequest(
    @Schema(description = "그룹 이름", required = true, example = "해달 짱")
    val name: String,

    @Schema(description = "비밀 그룹 여부", required = true, example = "false")
    val isPrivate: Boolean,

    @Schema(description = "비밀번호 (비밀 그룹 일 시)", required = false, example = "1q2w3e4r")
    var password: String?
)

fun CreateGroupRequest.toEntity(inviteCode: String, owner: User) =
    Group(name = name, isPrivate = isPrivate, password = password, inviteCode = inviteCode, owner = owner)