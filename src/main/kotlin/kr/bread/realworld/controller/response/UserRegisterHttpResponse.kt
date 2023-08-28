package kr.bread.realworld.controller.response

data class UserRegisterHttpResponse(
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)