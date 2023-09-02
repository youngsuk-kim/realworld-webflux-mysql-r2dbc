package kr.bread.realworld.provider.request

data class AuthRegisterHttpRequest(
    val username: String,
    val email: String,
    val password: String
)

data class AuthLoginHttpRequest(
    val email: String,
    val password: String
)
