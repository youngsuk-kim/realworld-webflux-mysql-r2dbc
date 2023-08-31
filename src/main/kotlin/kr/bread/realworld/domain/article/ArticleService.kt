package kr.bread.realworld.domain.article

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.domain.Favorite
import kr.bread.realworld.domain.user.FavoriteFinder
import kr.bread.realworld.domain.tag.TagAppender
import kr.bread.realworld.domain.tag.TagFinder
import kr.bread.realworld.domain.user.UserFindService
import kr.bread.realworld.domain.user.UserFollowService
import kr.bread.realworld.domain.user.UserResult
import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val tagFinder: TagFinder,
    private val tagAppender: TagAppender,
    private val userFindService: UserFindService,
    private val articleFinder: ArticleFinder,
    private val favoriteFinder: FavoriteFinder,
    private val articleAppender: ArticleAppender,
    private val userFollowService: UserFollowService,
    private val favoriteRepository: FavoriteRepository
) {

    private val log = KotlinLogging.logger {}

    suspend fun create(
        token: String,
        articleContent: ArticleContent,
    ): SingleArticle {
        val user = userFindService.findByToken(token)

        with(articleAppender.append(Article.of(articleContent, user.id))) {
            tagAppender.appendAll(articleContent.tagList, id!!)
            return getOne(slug)
        }
    }

    suspend fun getOne(slug: String): SingleArticle {
        val singleArticle = coroutineScope {
            val article = async {
                articleFinder.findBySlug(slug)
            }.await()

            async {
                val author = userFindService
                    .findById(article.userId)
                val favoritesCount = favoriteFinder
                    .findByArticleId(article.id!!)
                    .count()
                val tags = tagFinder
                    .findByArticleId(article.id!!)

                SingleArticle.create(
                    article = article,
                    favoritesCount = favoritesCount,
                    user = author,
                    tags = tags
                )
            }.await()
        }

        return singleArticle
    }

    suspend fun getFeed(
        token: String,
        limit: Int,
        offset: Int,
    ): List<SingleArticle> {
        val followeeList = userFollowService.findFollowee(token)

        val articles = followeeList.map {
            articleFinder.findAllArticle(it.id!!, offset, limit)
        }
            .flatten()


        return articles.map { article ->
            SingleArticle.create(
                article,
                article.favorites?.count(),
                UserResult.of(article.user, token),
                article.tags?.map { it.name }?.toSet()
            )
        }
    }

    suspend fun getArticles(
        token: String?,
        tag: String?,
        author: String?,
        favorited: String?,
        limit: Int,
        offset: Int,
    ): List<SingleArticle> {
        log.info { "tag: $tag , author: $author , favorited: $favorited , limit: $limit , offset: $offset" }
        val articles = articleFinder.findAllArticle(offset, limit, tag, author, favorited)

        return articles.map { article ->
            SingleArticle.create(
                article,
                article.favorites?.count(),
                UserResult.of(article.user, token),
                article.tags?.map { it.name }?.toSet()
            )
        }
    }

    suspend fun update(
        slug: String, title: String?, description: String?, body: String?
    ): SingleArticle {
        val article = articleFinder.findBySlug(slug)
        article.update(title, description, body)

        articleAppender.append(article)

        return getOne(article.slug)
    }

    suspend fun delete(slug: String): SingleArticle {
        val article = articleFinder.findBySlug(slug)
        article.delete()

        return getOne(article.slug)
    }

    suspend fun findTags(): List<String> {
        return tagFinder.findAll()
            .map { it.name }
    }

    suspend fun favorite(token: String, slug: String): SingleArticle {
        val favorite = coroutineScope {
            val userResultDeferred = async {
                userFindService.findByToken(token)
            }

            val articleDeferred = async {
                articleFinder.findBySlug(slug)
            }

            Favorite(userId = userResultDeferred.await().id, articleId = articleDeferred.await().id!!)
        }

        favoriteRepository.save(favorite)

        return getOne(slug)
    }

    suspend fun unFavorite(token: String, slug: String): SingleArticle {
        val article = articleFinder.findBySlug(slug)
        val favorite = favoriteRepository.findByArticleId(article.id!!).first()
        favorite.delete()

        favoriteRepository.save(favorite)

        return getOne(slug)
    }


}