package kr.bread.realworld.controller

data class ArticlePageable(
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val limit: Int = 20,
    val offset: Int = 0
)
