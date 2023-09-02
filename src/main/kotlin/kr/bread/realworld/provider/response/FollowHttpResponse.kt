package kr.bread.realworld.provider.response

data class UserFollowHttpResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)

data class UserUnFollowHttpResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
