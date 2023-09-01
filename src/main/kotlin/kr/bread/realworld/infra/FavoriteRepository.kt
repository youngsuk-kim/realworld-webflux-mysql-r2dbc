package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.user.Favorite
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface FavoriteRepository: CoroutineCrudRepository<Favorite, Long> {
    fun findByUserIdAndArticleId(userId: Long, articleId: Long): Mono<Favorite>
    fun findByArticleId(articleId: Long): Flow<Favorite>
    fun findByUserId(userId: Long): Flow<Favorite>
    fun findAllByArticleId(articleId: Long, pageable: Pageable): Flow<Favorite>
}