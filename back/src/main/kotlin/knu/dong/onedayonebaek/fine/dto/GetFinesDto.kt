package knu.dong.onedayonebaek.fine.dto

import io.swagger.v3.oas.annotations.media.Schema
import knu.dong.onedayonebaek.user.dto.UserDto

@Schema(description = "그룹원들의 벌금 조회 API의 응답 리스트의 원소")
data class GetFinesDto(
    @Schema(description = "유저 정보", nullable = false)
    val user: UserDto,

    @Schema(description = "벌금", nullable = false, example = "10000")
    val fine: Int
)
