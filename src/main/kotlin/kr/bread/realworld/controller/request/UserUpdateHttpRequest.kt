package kr.bread.realworld.controller.request

data class UserUpdateHttpRequest(
    val email: String?,
    val bio: String?,
    val image: String?
)
