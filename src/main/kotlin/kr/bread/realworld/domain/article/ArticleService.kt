package kr.bread.realworld.domain.article

import kr.bread.realworld.domain.follow.FollowFinder
import kr.bread.realworld.domain.tag.TagAppender
import kr.bread.realworld.domain.tag.TagFinder
import kr.bread.realworld.domain.tag.TagUpdater
import kr.bread.realworld.domain.user.UserFinder
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleFinder: ArticleFinder,
    private val articleAppender: ArticleAppender,
    private val followFinder: FollowFinder,
    private val tagAppender: TagAppender,
    private val userFinder: UserFinder,
    private val tagUpdater: TagUpdater,
    private val articleRemover: ArticleRemover
) {
    suspend fun create(token: String, content: ArticleContent): ArticleResult {
        val user = userFinder.findByToken(token)

        with(articleAppender.append(Article.of(content, user.id!!))) {
            tagAppender.appendAll(content.tagNames, id!!)
            return getOne(token, slug)
        }
    }

    suspend fun getOne(token: String? = null, slug: String) = articleFinder.findOne(token, slug)

    suspend fun getFeed(token: String, paging: ArticlePaging): List<ArticleResult> {
        val followeeList = followFinder.findFollowee(token)
        val user = userFinder.findByToken(token)

        if (followeeList.isNotEmpty()) {
            val articles = followeeList.map {
                articleFinder.findUsingPagingFilterByFollowee(it.followeeId, paging)
            }.flatten()

            return articles.map { article ->
                getOne(token, article.slug)
            }
        }

        return articleFinder.findByUserId(user.id!!).map { article ->
            getOne(token, article.slug)
        }
    }

    suspend fun getAll(token: String?, paging: ArticlePaging, condition: ArticleFilterCondition): List<ArticleResult> {
        val articles = articleFinder.findUsingPagingFilterByCondition(paging, condition)

        return articles.map { article ->
            getOne(token, article.slug)
        }
    }

    suspend fun update(token: String, content: ArticleUpdateContent): ArticleResult {
        val article = articleFinder.findOne(content.slug)
        article.update(content)
        articleAppender.append(article)

        if (content.tagList!!.isNotEmpty()) {
            tagUpdater.update(article.id!!, content.tagList)
        }

        return getOne(token, article.slug)
    }

    suspend fun delete(slug: String) {
        val article = articleFinder.findOne(slug)
        articleRemover.remove(article.id!!)
    }
}
