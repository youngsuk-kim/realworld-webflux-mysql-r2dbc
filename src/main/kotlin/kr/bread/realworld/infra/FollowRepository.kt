package kr.bread.realworld.infra

import kr.bread.realworld.domain.Follow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface FollowRepository: CoroutineCrudRepository<Follow, Long> {
    fun findByFollowerIdAndFolloweeId(followerId: Long, followeeId: Long): Mono<Follow>
}