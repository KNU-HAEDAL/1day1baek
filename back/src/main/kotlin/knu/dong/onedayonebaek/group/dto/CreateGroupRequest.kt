package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.user.domain.User

@Schema(description = "그룹 생성 요청 DTO")
data class CreateGroupRequest(
    @field:NotBlank
    @field:Size(min = 2, max = 100)
    @Schema(description = "그룹 이름", required = true, example = "해달 짱")
    val name: String,

    @Schema(description = "비밀 그룹 여부", required = true, example = "false")
    val isPrivate: Boolean,

    @Schema(description = "비밀번호 (비밀 그룹 일 시)", required = false, example = "1q2w3e4r")
    var password: String?,

    @field:Min(1)
    @field:Max(10000)
    @Schema(description = "하루에 풀어야하는 문제 개수", required = false, example = "1", defaultValue = "1")
    val goalSolveCount: Int = 1
)

fun CreateGroupRequest.toEntity(inviteCode: String, owner: User) =
    Group(name = name, isPrivate = isPrivate, password = password, inviteCode = inviteCode, owner = owner, goalSolveCount = goalSolveCount)