package kr.bread.realworld.controller.request

import kr.bread.realworld.domain.article.ArticleContent

data class ArticleCreateHttpRequest(
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>,
) {
    fun toArticleContent(): ArticleContent {
        return ArticleContent(title = title, description = description, body = body, tagList = tagList)
    }
}
