package kr.bread.realworld.controller.request

data class UserRegisterHttpRequest(
    val username: String,
    val email: String,
    val password: String
)