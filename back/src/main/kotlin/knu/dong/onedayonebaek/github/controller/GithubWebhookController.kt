package knu.dong.onedayonebaek.github.controller

import io.swagger.v3.oas.annotations.Hidden
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.github.dto.CommitWebhookRequest
import knu.dong.onedayonebaek.problem.service.ProblemService
import knu.dong.onedayonebaek.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/github/webhook")
@Hidden
class GithubWebhookController(
    private val userService: UserService,
    private val problemService: ProblemService
) {

    @PostMapping("/push")
    fun push(
        @RequestHeader(value = "X-GitHub-Event") githubEvent: String,
        @RequestBody request: CommitWebhookRequest
    ): ResponseEntity<String> {
        if (githubEvent == "ping") {
            return ResponseEntity.ok().body("valid webhook")
        } else if (githubEvent != "push") {
            return ResponseEntity.badRequest().body("Only allowed if 'X-Github-Event' is 'push'.")
        }

        val loginId: String = request.pusher.name

        val commits: List<CommitWebhookRequest.Commit> = request.commits
        val user = userService.findUserByLoginId(loginId).orElseThrow { NotFoundException() }

        problemService.addProblems(user, commits)

        return ResponseEntity.ok().build()
    }

}