package knu.dong.onedayonebaek.controller

import io.swagger.v3.oas.annotations.Operation
import knu.dong.onedayonebaek.service.GroupService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}