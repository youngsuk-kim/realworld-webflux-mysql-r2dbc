package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.favorite.Favorite
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface FavoriteRepository : CoroutineCrudRepository<Favorite, Long> {
    fun findByArticleId(articleId: Long): Flow<Favorite>
}
