package knu.dong.onedayonebaek.controller

import io.swagger.v3.oas.annotations.Operation
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.CreateGroupRequest
import knu.dong.onedayonebaek.dto.CreateGroupResponse
import knu.dong.onedayonebaek.exception.InvalidReqParamException
import knu.dong.onedayonebaek.service.GroupService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(
  private val groupService: GroupService
) {
    @Operation(
        summary = "공개된 그룹 목록 조회",
        description = "공개된 모든 그룹을 조회한다."
    )
    @GetMapping
    fun getPublicGroups() = groupService.getPublicGroups()

    @Operation(
        summary = "그룹 생성",
        description = "새로운 그룹을 만든다."
    )
    @PostMapping
    fun createGroup(
        @RequestBody requestDto: CreateGroupRequest,
        authentication: Authentication
    ): CreateGroupResponse {
        val user = authentication.principal as User

        if (requestDto.isPrivate && requestDto.password?.isBlank() != false) {
            throw InvalidReqParamException("비밀 그룹은 비밀번호가 필요합니다.")
        }

        return groupService.createGroup(user, requestDto)
    }
}