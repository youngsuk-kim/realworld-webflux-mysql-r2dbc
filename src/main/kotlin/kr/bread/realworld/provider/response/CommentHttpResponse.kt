package kr.bread.realworld.provider.response

import kr.bread.realworld.domain.follow.FollowerResult
import java.time.LocalDateTime

data class CommentHttpResponse(
    val id: Long,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: FollowerResult?
)
