package kr.bread.realworld.domain.article

import java.time.LocalDateTime

data class ArticleContent(
    val slug: String? = null,
    val title: String,
    val description: String,
    val body: String,
    val tagNames: Set<String>?,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    companion object {
        fun of(article: Article): ArticleContent {
            return ArticleContent(
                slug = article.slug,
                title = article.title,
                description = article.description,
                body = article.body,
                createdAt = article.createdAt,
                updatedAt = article.updatedAt,
                tagNames = article.tags?.map { it.name }?.toSet()
            )
        }
    }
}
