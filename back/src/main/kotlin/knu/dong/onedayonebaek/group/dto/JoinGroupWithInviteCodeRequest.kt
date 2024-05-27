package knu.dong.onedayonebaek.group.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "초대 코드로 그룹 가입 요청 DTO")
data class JoinGroupWithInviteCodeRequest(
    @Schema(description = "초대 코드", required = true, example = "q123we")
    var inviteCode: String
)