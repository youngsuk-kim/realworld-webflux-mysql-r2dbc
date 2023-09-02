package kr.bread.realworld.domain.article

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.bread.realworld.domain.follow.FollowFinder
import kr.bread.realworld.domain.tag.TagAppender
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.domain.user.UserResult
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleFinder: ArticleFinder,
    private val articleAppender: ArticleAppender,
    private val followFinder: FollowFinder,
    private val tagAppender: TagAppender,
    private val userFinder: UserFinder
) {

    private val log = KotlinLogging.logger {}

    suspend fun create(
        token: String,
        articleContent: ArticleContent
    ): ArticleResult {
        val user = userFinder.findByToken(token)

        with(articleAppender.append(Article.of(articleContent, user.id))) {
            tagAppender.appendAll(articleContent.tagNames, id!!)
            return articleFinder.findOneArticle(slug)
        }
    }

    suspend fun getOne(slug: String): ArticleResult = articleFinder.findOneArticle(slug)

    suspend fun getFeed(
        token: String,
        limit: Int,
        offset: Int
    ): List<ArticleResult> {
        val followeeList = followFinder.findFollowee(token)

        val articles = followeeList.map {
            articleFinder.findAllArticle(it.id!!, offset, limit)
        }
            .flatten()

        return articles.map { article ->
            ArticleResult.of(
                articleContent = ArticleContent.of(article),
                favoritesCount = article.getFavoriteCount(),
                userResult = UserResult.of(article.user, token)
            )
        }
    }

    suspend fun getMany(
        token: String?,
        tag: String?,
        author: String?,
        favorited: String?,
        limit: Int,
        offset: Int
    ): List<ArticleResult> {
        val articles = articleFinder.findAllArticle(offset, limit, tag, author, favorited)

        return articles.map { article ->
            ArticleResult.of(
                articleContent = ArticleContent.of(article),
                favoritesCount = article.getFavoriteCount(),
                userResult = UserResult.of(article.user, token)
            )
        }
    }

    suspend fun update(
        slug: String,
        title: String?,
        description: String?,
        body: String?
    ): ArticleResult {
        val article = articleFinder.findBySlug(slug)
        article.update(title, description, body)

        articleAppender.append(article)

        return articleFinder.findOneArticle((article.slug))
    }

    suspend fun delete(slug: String): ArticleResult {
        val article = articleFinder.findBySlug(slug).delete()

        return articleFinder.findOneArticle((article.slug))
    }
}
