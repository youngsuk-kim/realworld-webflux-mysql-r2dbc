package kr.bread.realworld.domain.favorite

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Component

@Component
class FavoriteFinder(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun findByArticleId(id: Long): Set<Favorite> {
        return favoriteRepository.findByArticleId(id)
            .buffer().toSet()
    }
}
