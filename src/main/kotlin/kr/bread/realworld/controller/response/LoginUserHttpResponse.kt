package kr.bread.realworld.controller.response

data class LoginUserHttpResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)
