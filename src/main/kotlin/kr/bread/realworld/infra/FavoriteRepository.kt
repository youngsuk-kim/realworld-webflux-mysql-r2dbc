package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.favorite.Favorite
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FavoriteRepository : CoroutineCrudRepository<Favorite, Long> {
    fun findByArticleId(articleId: Long): Flow<Favorite>
}
