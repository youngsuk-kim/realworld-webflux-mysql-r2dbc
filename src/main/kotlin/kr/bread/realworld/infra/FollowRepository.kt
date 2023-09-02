package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.follow.Follow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface FollowRepository : CoroutineCrudRepository<Follow, Long> {
    fun findByFollowerIdAndFolloweeId(followerId: Long, followeeId: Long): Mono<Follow>
    fun findByFolloweeId(followeeId: Long): Flow<Follow>
    fun existsByFolloweeIdAndFollowerId(followeeId: Long, followerId: Long): Boolean
}
