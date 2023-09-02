package kr.bread.realworld.domain.favorite

import kr.bread.realworld.infra.FavoriteRepository
import org.springframework.stereotype.Component

@Component
class FavoriteAppender(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun save(favorite: Favorite) {
        favoriteRepository.save(favorite)
    }
}
