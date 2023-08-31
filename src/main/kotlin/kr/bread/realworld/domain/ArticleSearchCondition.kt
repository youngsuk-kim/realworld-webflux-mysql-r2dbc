package kr.bread.realworld.domain

data class ArticleSearchCondition(
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val limit: Int,
    val offset: Int
)
