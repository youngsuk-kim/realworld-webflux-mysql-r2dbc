package kr.bread.realworld.domain

import kr.bread.realworld.controller.response.LoginUserHttpResponse
import kr.bread.realworld.controller.response.RegisterUserHttpResponse

data class UserResult(
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String?,
    val image: String? = null,
) {
    fun toRegisterResponse() =
        RegisterUserHttpResponse(
            email = this.email,
            token = this.token,
            username = this.username,
            bio = this.bio,
            image = this.image
        )

    fun toLoginUserResponse() =
        LoginUserHttpResponse(
            email = this.email,
            token = this.token!!,
            username = this.username,
            bio = this.bio,
            image = this.image
        )
}
