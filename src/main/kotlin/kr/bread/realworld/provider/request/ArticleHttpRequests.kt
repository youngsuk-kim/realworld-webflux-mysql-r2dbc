package kr.bread.realworld.provider.request

import kr.bread.realworld.domain.article.ArticleContent

data class ArticleCreateHttpRequest(
    val title: String,
    val description: String,
    val body: String,
    val tagNames: Set<String>
) {
    fun toArticleContent(): ArticleContent {
        return ArticleContent(title = title, description = description, body = body, tagNames = tagNames)
    }
}

data class ArticleUpdateHttpRequest(
    val title: String?,
    val description: String?,
    val body: String?
)
