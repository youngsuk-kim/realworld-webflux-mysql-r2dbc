package kr.bread.realworld.domain.follow

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kr.bread.realworld.domain.user.UserFinder
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val userFinder: UserFinder,
    private val followFinder: FollowFinder,
    private val followAppender: FollowAppender
) {

    suspend fun follow(token: String, followeeUsername: String): FollowerResult {
        val followee = coroutineScope {
            val deferredFollower = async {
                userFinder.findByToken(token)
            }

            val deferredFollowee = async {
                userFinder.findByUsername(followeeUsername)
            }

            followAppender.save(
                Follow(
                    followerId = deferredFollower.await().id!!,
                    followeeId = deferredFollowee.await().id!!
                )
            )

            deferredFollowee
        }.await()

        return FollowerResult.of(followee, true)
    }

    suspend fun unfollow(token: String, followeeUsername: String): FollowerResult {
        val followee = coroutineScope {
            val deferredFollower = async {
                userFinder.findByToken(token)
            }

            val deferredFollowee = async {
                userFinder.findByUsername(followeeUsername)
            }

            followFinder.findFollow(
                deferredFollower.await().id!!,
                deferredFollowee.await().id!!
            ).unfollow()

            return@coroutineScope deferredFollowee
        }.await()

        return FollowerResult.of(followee, false)
    }

    suspend fun findFollow(token: String, followeeId: Long): FollowerResult {
        val follower = userFinder.findByToken(token)
        val isFollower = followFinder.isFollower(follower.id!!, followeeId)

        return FollowerResult.of(follower, isFollower)
    }
}
