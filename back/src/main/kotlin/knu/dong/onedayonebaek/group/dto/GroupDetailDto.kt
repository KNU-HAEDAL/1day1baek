package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.user.dto.UserDto
import knu.dong.onedayonebaek.user.dto.toUserDto

@Schema(description = "그룹 생성 응답 DTO")
data class GroupDetailDto(
    @Schema(description = "그룹 ID", nullable = false, example = "1")
    val id: Long,

    @Schema(description = "그룹 이름", nullable = false, example = "해달 짱")
    val name: String,

    @Schema(description = "비밀 그룹 여부", nullable = false, example = "false")
    val isPrivate: Boolean,

    @Schema(description = "초대 코드", nullable = false, example = "as231g")
    val inviteCode: String,

    @Schema(description = "하루에 풀어야하는 문제 개수", nullable = false, example = "1")
    val goalSolveCount: Int,

    @Schema(description = "문제를 풀지 않은 날 매겨지는 벌금", nullable = false, example = "1000")
    val fine: Int,

    @Schema(description = "그룹에 속한 유저", nullable = false)
    val users: List<UserDto>,

    @Schema(description = "그룹장", nullable = false)
    val owner: UserDto
)

fun Group.toGroupDetailDto() =
    GroupDetailDto(
        id!!,
        name,
        isPrivate,
        inviteCode,
        goalSolveCount,
        fine,
        users.map { it.user.toUserDto() },
        owner.toUserDto()
    )

