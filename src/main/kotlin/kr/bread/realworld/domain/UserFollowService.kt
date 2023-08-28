package kr.bread.realworld.domain

import kr.bread.realworld.infra.FollowRepository
import kr.bread.realworld.infra.UserRepository
import org.springframework.stereotype.Service

@Service
class UserFollowService(
    private val userFindService: UserFindService,
    private val followRepository: FollowRepository
) {

    suspend fun follow(token: String, followeeId: Long): ProfileResult {
        val user = userFindService.findByToken(token)

        val follow = followRepository.save(
            Follow(
                followerId = user.id,
                followeeId = followeeId
            )
        )

        return ProfileResult(
            username = user.username,
            bio = user.bio,
            image = user.image,
            following = true
        )
    }
}