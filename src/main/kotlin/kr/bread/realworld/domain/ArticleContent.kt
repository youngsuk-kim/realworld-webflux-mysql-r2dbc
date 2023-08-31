package kr.bread.realworld.domain

data class ArticleContent (
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>?,
)