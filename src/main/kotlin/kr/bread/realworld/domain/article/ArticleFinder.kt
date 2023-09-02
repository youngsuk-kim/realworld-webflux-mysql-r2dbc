package kr.bread.realworld.domain.article

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.domain.favorite.FavoriteFinder
import kr.bread.realworld.domain.tag.TagFinder
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.domain.user.UserResult
import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.support.exception.ArticleNotFoundException
import kr.bread.realworld.support.exception.TagNotFoundException
import kr.bread.realworld.support.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class ArticleFinder(
    private val articleRepository: ArticleRepository,
    private val userFinder: UserFinder,
    private val tagFinder: TagFinder,
    private val favoriteFinder: FavoriteFinder
) {

    suspend fun findAllArticle(
        offset: Int,
        limit: Int,
        tag: String?,
        author: String?,
        favorited: String?
    ): Set<Article> {
        var articles = articleRepository.findAll()
            .map { it.makeRelation(favoriteFinder.findByArticleId(it.id!!), tagFinder.findTagsByArticleId(it.id!!)) }
            .map { it.setUser(userFinder.findUserById(it.userId) ?: throw UserNotFoundException()) }

        articles = filterBy(tag, articles, author, favorited)

        return articles
            .drop(offset)
            .take(limit).toSet()
    }

    suspend fun findAllArticle(
        userId: Long,
        offset: Int,
        limit: Int
    ): Set<Article> {
        return articleRepository.findAll()
            .filter { it.userId == userId }
            .drop(offset)
            .take(limit).toSet()
    }

    suspend fun findBySlug(slug: String): Article {
        return articleRepository.findBySlug(slug)
            .awaitSingleOrNull() ?: throw ArticleNotFoundException()
    }

    suspend fun findOneArticle(slug: String): ArticleResult {
        val articleResult = coroutineScope {
            val article = async {
                findBySlug(slug)
            }.await()

            async {
                val author = userFinder
                    .findById(article.userId)
                val favoritesCount = favoriteFinder
                    .findByArticleId(article.id!!)
                    .count()
                val tags = tagFinder
                    .findByArticleId(article.id!!)

                ArticleResult.of(
                    articleContent = ArticleContent.of(article),
                    favoritesCount = article.getFavoriteCount(),
                    userResult = UserResult.of(article.user)
                )
            }.await()
        }

        return articleResult
    }

    private suspend fun filterBy(
        tag: String?,
        articles: Flow<Article>,
        author: String?,
        favorited: String?
    ): Flow<Article> {
        var filterArticles = articles

        if (tag != null) {
            filterArticles = articles.filter {
                it.tags?.map { it.name }?.contains(tag) ?: throw TagNotFoundException()
            }
        }

        if (author != null) {
            filterArticles = articles.filter {
                it.user?.username == author
            }
        }

        if (favorited != null) {
            val user = userFinder.findByUsername(favorited)
            filterArticles = articles.filter { article ->
                article.favorites?.map { it.userId }?.contains(user.id) ?: throw UserNotFoundException()
            }
        }
        return filterArticles
    }
}
