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
    private val favoriteAppender: FavoriteAppender,
    private val favoriteRemover: FavoriteRemover
) {

    suspend fun favorite(token: String, slug: String): ArticleResult {
        val favorite = coroutineScope {
            val userResultDeferred = async {
                userFinder.findByToken(token)
            }

            val articleDeferred = async {
                articleFinder.findOne(slug)
            }

            Favorite(userId = userResultDeferred.await().id!!, articleId = articleDeferred.await().id!!)
        }

        favoriteAppender.save(favorite)

        return articleFinder.findOne(token, slug)
    }

    suspend fun unFavorite(token: String, slug: String): ArticleResult {
        val article = articleFinder.findOne(slug)
        val favorite = favoriteFinder.findByArticleId(article.id!!).first()
        favoriteRemover.remove(favorite)

        return articleFinder.findOne(token, slug)
    }
}
