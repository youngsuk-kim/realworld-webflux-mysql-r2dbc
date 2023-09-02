package kr.bread.realworld.domain.article

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.domain.favorite.FavoriteFinder
import kr.bread.realworld.domain.follow.FollowFinder
import kr.bread.realworld.domain.tag.TagFinder
import kr.bread.realworld.domain.user.User
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.domain.user.UserResult
import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.support.exception.ArticleNotFoundException
import kr.bread.realworld.support.exception.TagNotFoundException
import org.springframework.stereotype.Component

@Component
class ArticleFinder(
    private val articleRepository: ArticleRepository,
    private val userFinder: UserFinder,
    private val tagFinder: TagFinder,
    private val favoriteFinder: FavoriteFinder,
    private val followFinder: FollowFinder
) {

    suspend fun findUsingPagingFilterByCondition(
        articlePaging: ArticlePaging,
        articleFilterCondition: ArticleFilterCondition
    ): Set<Article> {
        var articles = createArticles()

        articles = filterBy(articles, articleFilterCondition)

        return articles
            .drop(articlePaging.offset)
            .take(articlePaging.limit).toSet()
    }

    private fun createArticles() = articleRepository.findAll()
        .map { article ->
            with(article) {
                article.of(
                    user = userFinder.findById(userId),
                    favorites = favoriteFinder.findByArticleId(id()),
                    tags = tagFinder.findAll(id())
                )
            }
        }

    suspend fun findUsingPagingFilterByAuthor(
        userId: Long,
        articlePaging: ArticlePaging
    ): Set<Article> {
        return articleRepository.findAll()
            .filter { it.checkSameAuthor(userId) }
            .drop(articlePaging.offset)
            .take(articlePaging.limit)
            .toSet()
    }

    suspend fun findOne(slug: String): Article {
        return articleRepository.findBySlug(slug)
            .awaitSingleOrNull() ?: throw ArticleNotFoundException()
    }

    private suspend fun filterBy(
        articles: Flow<Article>,
        condition: ArticleFilterCondition
    ): Flow<Article> {
        fun filterByTag() = articles.filter { article ->
            article.tags?.map { it.name }?.contains(condition.tag) ?: throw TagNotFoundException()
        }

        fun filterByAuthor() = articles.filter {
            checkNotNull(condition.author) { "author cannot be null" }
            it.checkSameAuthor(condition.author)
        }

        fun filterByFavorited(user: User): Flow<Article> {
            return articles.filter { article ->
                article.favorites.map { it.userId }.contains(user.id)
            }
        }

        if (condition.tag != null) {
            filterByTag()
        }

        if (condition.author != null) {
            filterByAuthor()
        }

        if (condition.favorited != null) {
            val user = userFinder.findByUsername(condition.favorited)
            filterByFavorited(user)
        }
        return articles
    }

    suspend fun findOne(token: String?, slug: String): ArticleResult {
        val article = findOne(slug)
        val favoriteCount = favoriteFinder.countFavorite(article.id())
        val author = userFinder.findById(article.userId)

        if (!token.isNullOrBlank()) {
            return loginUserArticleResult(token, article, favoriteCount, author)
        }

        return ArticleResult.of(
            articleContent = ArticleContent.of(article),
            favoritesCount = favoriteCount,
            followAuthor = false,
            author = UserResult.of(author),
            favorited = false
        )
    }

    private suspend fun loginUserArticleResult(
        token: String?,
        article: Article,
        favoriteCount: Int,
        author: User
    ): ArticleResult {
        requireNotNull(token) { "token cannot be null" }

        val user = userFinder.findByToken(token)
        val isFollower = followFinder.isFollower(user.id(), article.id())
        val isFavoriteArticle = favoriteFinder.isFavoriteArticle(article.id(), user.id())

        return ArticleResult.of(
            articleContent = ArticleContent.of(article),
            favoritesCount = favoriteCount,
            followAuthor = isFollower,
            author = UserResult.of(author),
            favorited = isFavoriteArticle
        )
    }
}
