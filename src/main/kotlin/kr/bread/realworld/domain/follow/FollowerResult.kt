package kr.bread.realworld.domain.follow

import kr.bread.realworld.provider.response.UserFollowHttpResponse
import kr.bread.realworld.provider.response.UserUnFollowHttpResponse

data class FollowerResult(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean = false
) {
    fun toFollowResponse(): UserFollowHttpResponse {
        return UserFollowHttpResponse(
            username = username,
            bio = bio,
            image = image,
            following = following
        )
    }

    fun toUnFollowResponse(): UserUnFollowHttpResponse {
        return UserUnFollowHttpResponse(
            username = username,
            bio = bio,
            image = image,
            following = following
        )
    }
}
