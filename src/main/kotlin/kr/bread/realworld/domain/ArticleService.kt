package kr.bread.realworld.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.infra.FavoriteRepository
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val tagRepository: TagRepository,
    private val userFindService: UserFindService,
    private val favoriteRepository: FavoriteRepository,
    private val followService: UserFollowService,
) {

    suspend fun create(
        token: String,
        title: String,
        description: String,
        body: String,
        tagList: List<String>?,
    ) {
        val user = userFindService.findByToken(token)

        val article = articleRepository.save(
            Article.of(
                title = title,
                description = description,
                body = body,
                userId = user.id
            )
        )

        if (!tagList.isNullOrEmpty()) {
            tagRepository.saveAll(tagList.map { Tag(name = it, articleId = article.id!!) })
        }
    }

    suspend fun getOne(slug: String): SingleArticle {
        val singleArticle = coroutineScope {
            val article = async {
                articleRepository.findBySlug(slug).awaitSingleOrNull()
            }.await()

            async {
                val author = userFindService.findById(article?.userId)
                val favorites = favoriteRepository.findByArticleId(article?.id!!).buffer().toList()
                val tags = tagRepository.findByArticleId(article.id!!).buffer().toList()

                SingleArticle.create(
                    article = article,
                    favoritesCount = favorites.count(),
                    user = author,
                    tags = tags.map { it.name }
                )
            }
        }

        return singleArticle.await()
    }


}