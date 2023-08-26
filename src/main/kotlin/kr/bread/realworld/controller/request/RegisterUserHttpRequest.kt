package kr.bread.realworld.controller.request

data class RegisterUserHttpRequest(
    val username: String,
    val email: String,
    val password: String
)