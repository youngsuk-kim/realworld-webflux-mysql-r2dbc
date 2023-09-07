package kr.bread.realworld.provider.response

import java.time.LocalDateTime
import kr.bread.realworld.domain.follow.FollowerResult

data class ArticleHttpResponse(
    val favorited: Boolean,
    val favoritesCount: Int = 0,
    val slug: String? = null,
    val title: String,
    val description: String,
    val body: String,
    val tagList: Set<String>?,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val author: FollowerResult
)
