package kr.bread.realworld.domain.follow

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.infra.FollowRepository
import kr.bread.realworld.support.exception.FollowNotFoundException
import org.springframework.stereotype.Component

@Component
class FollowFinder(
    private val followRepository: FollowRepository,
    private val userFinder: UserFinder
) {

    suspend fun findFollowee(token: String): List<Follow> {
        val follower = userFinder.findByToken(token)

        return followRepository.findByFolloweeId(follower.id())
            .buffer().toList()
    }

    suspend fun findFollow(followerId: Long, followeeId: Long): Follow {
        return followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
            .awaitSingleOrNull() ?: throw FollowNotFoundException()
    }

    suspend fun isFollower(followeeId: Long, followerId: Long) = followRepository.existsByFolloweeIdAndFollowerId(followeeId, followerId)
}
