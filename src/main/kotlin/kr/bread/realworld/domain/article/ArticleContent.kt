package kr.bread.realworld.domain.article

data class ArticleContent (
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>?,
)