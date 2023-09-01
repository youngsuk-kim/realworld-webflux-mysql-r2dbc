package kr.bread.realworld.domain.user

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.domain.article.Article
import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Component

@Component
class FavoriteFinder(
    private val favoriteRepository: FavoriteRepository,
    private val userFindService: UserFindService
) {

    suspend fun filterByFavoriteUserName(username: String, articles: Set<Article>): Set<Article> {
        val user = userFindService.findByUsername(username)
        val favorite = favoriteRepository.findByUserId(user.id)
            .buffer().toSet()

        val articleIds = favorite.map { it.articleId }
        return articles.filter { articleIds.contains(it.id) }.toSet()
    }

    suspend fun findByArticleId(id: Long): Set<Favorite> {
        return favoriteRepository.findByArticleId(id)
            .buffer().toSet()
    }

}