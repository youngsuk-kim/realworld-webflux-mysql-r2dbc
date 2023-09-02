package kr.bread.realworld.domain.follow

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.infra.FollowRepository
import kr.bread.realworld.support.exception.NoFollowExistException
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val userFinder: UserFinder,
    private val followRepository: FollowRepository
) {

    suspend fun follow(token: String, followeeUsername: String): FollowerResult {
        val user = coroutineScope {
            val deferredFollower = async {
                userFinder.findByToken(token)
            }

            val deferredFollowee = async {
                userFinder.findByUsername(followeeUsername)
            }

            followRepository.save(
                Follow(
                    followerId = deferredFollower.await().id,
                    followeeId = deferredFollowee.await().id
                )
            )

            deferredFollower
        }.await()

        return FollowerResult(
            username = user.username,
            bio = user.bio,
            image = user.image,
            following = true
        )
    }

    suspend fun unfollow(token: String, followeeUsername: String): FollowerResult {
        val user = coroutineScope {
            val deferredFollower = async {
                userFinder.findByToken(token)
            }

            val deferredFollowee = async {
                userFinder.findByUsername(followeeUsername)
            }

            followRepository.findByFollowerIdAndFolloweeId(
                deferredFollower.await().id,
                deferredFollowee.await().id
            ).awaitSingleOrNull()?.unfollow() ?: throw NoFollowExistException()

            deferredFollower
        }.await()

        return FollowerResult(
            username = user.username,
            bio = user.bio,
            image = user.image,
            following = false
        )
    }

    suspend fun findFollow(token: String, followeeUsername: String): FollowerResult {
        val user = coroutineScope {
            val deferredFollower = async {
                userFinder.findByToken(token)
            }

            val deferredFollowee = async {
                userFinder.findByUsername(followeeUsername)
            }

            followRepository.findByFollowerIdAndFolloweeId(
                deferredFollower.await().id,
                deferredFollowee.await().id
            ).awaitSingleOrNull() ?: throw NoFollowExistException()

            deferredFollower
        }.await()

        return FollowerResult(
            username = user.username,
            bio = user.bio,
            image = user.image,
            following = false
        )
    }
}
