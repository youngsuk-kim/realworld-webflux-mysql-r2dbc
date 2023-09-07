package kr.bread.realworld.domain.favorite

import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Component

@Component
class FavoriteRemover(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun remove(favorite: Favorite) {
        favoriteRepository.delete(favorite)
    }
}