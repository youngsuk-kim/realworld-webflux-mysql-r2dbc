package kr.bread.realworld.provider.response

data class UserLoginHttpResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)

data class UserRegisterHttpResponse(
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)

