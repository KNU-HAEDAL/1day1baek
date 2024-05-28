package knu.dong.onedayonebaek.github.dto

import java.time.LocalDateTime

data class CommitWebhookRequest(
    val pusher: Pusher,
    val commits: List<Commit> = ArrayList()
) {
    data class Commit (val url: String, val message: String, val timestamp: LocalDateTime)
    data class Pusher (val name: String, val email: String)
}
