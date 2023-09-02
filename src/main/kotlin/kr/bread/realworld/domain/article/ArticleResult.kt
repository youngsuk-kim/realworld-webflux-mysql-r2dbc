package kr.bread.realworld.domain.article

import kr.bread.realworld.domain.follow.FollowerResult
import kr.bread.realworld.domain.user.UserResult
import kr.bread.realworld.provider.response.ArticleHttpResponse

data class ArticleResult(
    val favorited: Boolean,
    val favoritesCount: Int = 0,
    val articleContent: ArticleContent,
    val author: FollowerResult
) {
    companion object {
        fun of(articleContent: ArticleContent, favoritesCount: Int, followAuthor: Boolean, author: UserResult, favorited: Boolean): ArticleResult {
            return ArticleResult(
                favoritesCount = favoritesCount,
                articleContent = articleContent,
                author = FollowerResult(
                    username = author.username,
                    bio = author.bio,
                    image = author.image,
                    following = followAuthor
                ),
                favorited = favorited
            )
        }
    }

    fun toArticleHttpResponse(): ArticleHttpResponse {
        return ArticleHttpResponse(
            favorited = this.favorited,
            favoritesCount = this.favoritesCount,
            articleContent = this.articleContent,
            author = this.author
        )
    }
}
