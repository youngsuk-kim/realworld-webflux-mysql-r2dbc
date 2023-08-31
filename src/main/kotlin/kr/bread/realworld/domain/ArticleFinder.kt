package kr.bread.realworld.domain

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.support.exception.ArticleNotFoundException
import kr.bread.realworld.support.exception.TagNotFoundException
import kr.bread.realworld.support.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class ArticleFinder(
    private val articleRepository: ArticleRepository,
    private val userFindService: UserFindService,
    private val tagFinder: TagFinder,
    private val favoriteFinder: FavoriteFinder,
) {

    suspend fun findAllArticle(offset: Int, limit: Int, tag: String?, author: String?, favorited: String?): Set<Article> {
        var articles = articleRepository.findAll()
            .map { it.makeRelation(favoriteFinder.findByArticleId(it.id!!), tagFinder.findTagsByArticleId(it.id!!)) }
            .map { it.setUser(userFindService.findUserById(it.userId) ?: throw UserNotFoundException()) }

        if (tag != null) {
            articles =articles.filter {
                    it.tags?.map { it.name }?.contains(tag) ?: throw TagNotFoundException()
                }
        }

        if (author != null) {
            articles = articles.filter {
                it.user?.username == author
            }
        }

        if (favorited != null) {
            val user = userFindService.findByUsername(favorited)
            articles = articles.filter { article ->
                article.favorites?.map { it.userId }?.contains(user.id) ?: throw UserNotFoundException()
            }
        }


        return articles
            .drop(offset)
            .take(limit).toSet()
    }

    suspend fun findByUserId(userId: Long): Article? {
        return articleRepository.findByUserId(userId)
            .awaitSingleOrNull()
    }

    suspend fun findBySlug(slug: String): Article {
        return articleRepository.findBySlug(slug)
            .awaitSingleOrNull() ?: throw ArticleNotFoundException()
    }

    suspend fun findBy(tagName: String): Set<Article> {
        val articleIds = tagFinder.getArticleIdsBy(tagName)
        return articleRepository.findByIdIn(articleIds)
            .buffer().toSet()
    }

    suspend fun filterByUserName(username: String, articles: Set<Article>): Set<Article> {
        val user = userFindService.findByUsername(username)
        return articles.filter { it.userId == user.id }.toSet()
    }

    suspend fun limitAndSkipArticles(limit: Int, offset: Int, articles: Set<Article>): Set<Article> {
        return articles.asFlow().drop(offset).take(limit).toSet()
    }

    suspend fun findArticles(articleSearchCondition: ArticleSearchCondition): Set<SingleArticle> {

        val tags = articleSearchCondition.tag?.let { tagFinder.getArticleIdsBy(articleSearchCondition.tag) }


        return articleRepository.findArticlesByPaging(articleSearchCondition.limit, articleSearchCondition.offset)
            .buffer().toSet()
    }
}