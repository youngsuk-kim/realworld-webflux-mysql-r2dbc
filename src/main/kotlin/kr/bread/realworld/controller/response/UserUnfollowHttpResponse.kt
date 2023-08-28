package kr.bread.realworld.controller.response

data class UserUnfollowHttpResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
