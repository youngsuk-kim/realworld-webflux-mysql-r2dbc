package kr.bread.realworld.domain.article

import kr.bread.realworld.domain.follow.FollowFinder
import kr.bread.realworld.domain.tag.TagAppender
import kr.bread.realworld.domain.user.UserFinder
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleFinder: ArticleFinder,
    private val articleAppender: ArticleAppender,
    private val followFinder: FollowFinder,
    private val tagAppender: TagAppender,
    private val userFinder: UserFinder
) {
    suspend fun create(token: String, content: ArticleContent): ArticleResult {
        val user = userFinder.findByToken(token)

        with(articleAppender.append(Article.of(content, user.id()))) {
            tagAppender.appendAll(content.tagNames, id())
            return getOne(token, slug)
        }
    }

    suspend fun getOne(token: String? = null, slug: String) = articleFinder.findOne(token, slug)

    suspend fun getFeed(token: String, paging: ArticlePaging): List<ArticleResult> {
        val followeeList = followFinder.findFollowee(token)

        val articles = followeeList.map {
            articleFinder.findUsingPagingFilterByAuthor(it.id(), paging)
        }.flatten()

        return articles.map { article ->
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

        return getOne(token, article.slug)
    }

    suspend fun delete(slug: String) = articleFinder.findOne(slug).delete()
}
