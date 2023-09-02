package kr.bread.realworld.domain.comment

import kr.bread.realworld.domain.follow.FollowerResult
import java.time.LocalDateTime

data class CommentResult(
    val id: Long,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: FollowerResult?
) {
    companion object {
        fun of(comment: Comment, followerResult: FollowerResult?): CommentResult {
            return CommentResult(
                id = comment.id!!,
                body = comment.body,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt!!,
                author = followerResult
            )
        }
    }
}
