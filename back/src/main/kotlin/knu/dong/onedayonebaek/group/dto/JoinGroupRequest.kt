package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "그룹 가입 요청 DTO")
data class JoinGroupRequest(
    @Schema(description = "비밀번호 (비밀 그룹 일 시)", required = false, example = "1q2w3e4r")
    var password: String?
)