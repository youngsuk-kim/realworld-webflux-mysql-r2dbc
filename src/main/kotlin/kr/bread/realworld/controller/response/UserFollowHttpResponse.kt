package kr.bread.realworld.controller.response

data class UserFollowHttpResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)


