package kr.bread.realworld.controller.request

data class CreateUserHttpRequest(
    val username: String,
    val email: String,
    val password: String
)