package kr.bread.realworld.provider.response

import kr.bread.realworld.domain.article.ArticleContent
import kr.bread.realworld.domain.follow.FollowerResult

data class ArticleHttpResponse(
    val favorited: Boolean,
    val favoritesCount: Int = 0,
    val articleContent: ArticleContent,
    val author: FollowerResult
)
