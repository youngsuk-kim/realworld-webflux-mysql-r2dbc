package kr.bread.realworld.domain.favorite

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kr.bread.realworld.domain.article.ArticleFinder
import kr.bread.realworld.domain.article.ArticleResult
import kr.bread.realworld.domain.user.UserFinder
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    private val userFinder: UserFinder,
    private val articleFinder: ArticleFinder,
    private val favoriteFinder: FavoriteFinder,
    private val favoriteAppender: FavoriteAppender
) {

    suspend fun favorite(token: String, slug: String): ArticleResult {
        val favorite = coroutineScope {
            val userResultDeferred = async {
                userFinder.findByToken(token)
            }

            val articleDeferred = async {
                articleFinder.findBySlug(slug)
            }

            Favorite(userId = userResultDeferred.await().id, articleId = articleDeferred.await().id!!)
        }

        favoriteAppender.save(favorite)

        return articleFinder.findOneArticle(slug)
    }

    suspend fun unFavorite(token: String, slug: String): ArticleResult {
        val article = articleFinder.findBySlug(slug)
        val favorite = favoriteFinder.findByArticleId(article.id!!).first()
        favorite.delete()

        favoriteAppender.save(favorite)

        return articleFinder.findOneArticle(slug)
    }
}
