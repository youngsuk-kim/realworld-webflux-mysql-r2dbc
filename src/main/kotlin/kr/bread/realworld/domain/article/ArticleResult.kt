package kr.bread.realworld.domain.article

import kr.bread.realworld.domain.follow.FollowerResult
import kr.bread.realworld.domain.user.UserResult
import java.time.LocalDateTime

data class ArticleResult(
    val favorited: Boolean,
    val favoritesCount: Int?,
    val articleContent: ArticleContent,
    val author: FollowerResult
) {
    companion object {

        fun of(articleContent: ArticleContent, favoritesCount: Int?, userResult: UserResult): ArticleResult {
            return ArticleResult(
                favoritesCount = favoritesCount,
                articleContent = articleContent,
                author = FollowerResult(
                    username = userResult.username,
                    bio = userResult.bio,
                    image = userResult.image,
                    following = false
                ),
                favorited = false
            )
        }
    }
}
