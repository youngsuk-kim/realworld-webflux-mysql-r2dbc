package kr.bread.realworld.domain.follow

import kr.bread.realworld.infra.FollowRepository
import org.springframework.stereotype.Component

@Component
class FollowAppender(
    private val followRepository: FollowRepository
) {

    suspend fun save(follow: Follow) = followRepository.save(follow)
}
