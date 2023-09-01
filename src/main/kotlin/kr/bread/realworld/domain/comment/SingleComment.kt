package kr.bread.realworld.domain.comment

import java.time.LocalDateTime
import kr.bread.realworld.domain.user.ProfileResult

data class SingleComment(
    val id: Long,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: ProfileResult?
) {
    companion object {
        fun of(comment: Comment, profileResult: ProfileResult?): SingleComment {
            return SingleComment(
                id = comment.id!!,
                body = comment.body,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt!!,
                author = profileResult
            )
        }
    }
}
