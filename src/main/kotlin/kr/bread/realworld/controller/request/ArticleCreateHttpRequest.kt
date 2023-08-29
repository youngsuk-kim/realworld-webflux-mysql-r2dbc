package kr.bread.realworld.controller.request

data class ArticleCreateHttpRequest(
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>
)
