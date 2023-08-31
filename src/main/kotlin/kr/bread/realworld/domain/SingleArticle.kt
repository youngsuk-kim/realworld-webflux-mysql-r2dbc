package kr.bread.realworld.domain

import java.time.LocalDateTime

data class SingleArticle (
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val favorited: Boolean,
    val favoritesCount: Int?,
    val tagList: Set<String>?,
    val author: ProfileResult
) {
    companion object {
        fun create(article: Article, favoritesCount: Int?, user: UserResult, tags: Set<String>?): SingleArticle {
            return SingleArticle(
                slug = article.slug,
                title = article.title,
                description = article.description,
                body = article.body,
                createdAt = article.createdAt,
                updatedAt = article.updatedAt,
                favoritesCount = favoritesCount,
                author = ProfileResult(
                    username = user.username,
                    bio = user.bio,
                    image = user.image,
                    following = false
                ),
                tagList = tags,
                favorited = false
            )
        }
    }
}