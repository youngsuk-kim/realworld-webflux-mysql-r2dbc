package kr.bread.realworld.domain.follow

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toList
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.infra.FollowRepository
import org.springframework.stereotype.Component

@Component
class FollowFinder(
    private val followRepository: FollowRepository,
    private val userFinder: UserFinder
) {

    suspend fun findFollowee(token: String): List<Follow> {
        val follower = userFinder.findByToken(token)

        return followRepository.findByFolloweeId(follower.id)
            .buffer().toList()
    }
}
