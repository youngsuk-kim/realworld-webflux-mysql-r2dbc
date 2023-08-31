package kr.bread.realworld.domain.user

import kr.bread.realworld.controller.response.UserFollowHttpResponse
import kr.bread.realworld.controller.response.UserUnfollowHttpResponse

data class ProfileResult(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
) {
    fun toFollowResponse(): UserFollowHttpResponse {
        return UserFollowHttpResponse(
            username = username,
            bio = bio,
            image = image,
            following = following
        )
    }

    fun toUnFollowResponse(): UserUnfollowHttpResponse {
        return UserUnfollowHttpResponse(
            username = username,
            bio = bio,
            image = image,
            following = following
        )
    }
}
