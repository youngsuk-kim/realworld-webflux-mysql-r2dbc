package kr.bread.realworld.provider.response

data class UserCurrentHttpResponse(
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)
data class UserUpdateHttpResponse(
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)
