package kr.bread.realworld.domain.favorite

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Component

@Component
class FavoriteFinder(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun findByArticleId(id: Long): Set<Favorite> =
        favoriteRepository.findByArticleId(id).buffer().toSet()

    suspend fun isFavoriteArticle(articleId: Long, userId: Long) =
        favoriteRepository.existsByUserIdAndArticleId(articleId, userId)

    suspend fun countFavorite(articleId: Long) = favoriteRepository.countByArticleId(articleId)
}
