package kr.bread.realworld.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kr.bread.realworld.support.exception.TagNotFoundException
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val tagFinder: TagFinder,
    private val tagAppender: TagAppender,
    private val userFindService: UserFindService,
    private val articleFinder: ArticleFinder,
    private val favoriteFinder: FavoriteFinder,
    private val articleAppender: ArticleAppender,
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

        return articles.map { SingleArticle.create(it, it.favorites?.count(), UserResult.of(it.user, token), it.tags?.map { it.name }?.toSet()) }
    }


}