package kr.bread.realworld.domain

import kr.bread.realworld.controller.response.UserFollowHttpResponse

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
}
