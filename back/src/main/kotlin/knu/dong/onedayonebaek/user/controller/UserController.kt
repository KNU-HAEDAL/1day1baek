package knu.dong.onedayonebaek.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import knu.dong.onedayonebaek.common.exception.response.UnauthorizedResponse
import knu.dong.onedayonebaek.group.dto.GroupOfListDto
import knu.dong.onedayonebaek.group.service.GroupService
import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.user.dto.toUserDto
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "User APIs", description = "유저 정보와 관련된 APIs")
class UserController(
    private val groupService: GroupService
) {

    @Operation(
        summary = "로그인된 유저의 정보 조회",
        description = "로그인된 유저의 정보를 조회한다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로그인된 유저 정보"),
        ApiResponse(
            responseCode = "401", description = "로그인 되지 않음",
            content = [Content(
                examples = [ExampleObject(
                    name = "로그인 되지 않음",
                    value = "{\"code\": \"access_token_expired\", \"message\":\"Access token expired\"}"
                )],
                schema = Schema(implementation = UnauthorizedResponse::class))],
        )
    )
    @GetMapping
    fun loginUserInfo(authentication: Authentication) = (authentication.principal as User).toUserDto()


    @GetMapping("/my/groups")
    fun getMyGroups(authentication: Authentication): List<GroupOfListDto> {
        val user = authentication.principal as User

        return groupService.getGroupsOfUser(user)
    }
}