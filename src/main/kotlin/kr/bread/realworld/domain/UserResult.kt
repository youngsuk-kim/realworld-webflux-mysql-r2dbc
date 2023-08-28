package kr.bread.realworld.domain

import kr.bread.realworld.controller.response.UserCurrentHttpResponse
import kr.bread.realworld.controller.response.UserLoginHttpResponse
import kr.bread.realworld.controller.response.UserRegisterHttpResponse
import kr.bread.realworld.controller.response.UserUpdateHttpResponse

data class UserResult(
    val id: Long,
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String?,
    val image: String? = null,
) {
    fun toRegisterResponse() =
        UserRegisterHttpResponse(
            email = this.email,
            token = this.token,
            username = this.username,
            bio = this.bio,
            image = this.image
        )

    fun toLoginUserResponse() =
        UserLoginHttpResponse(
            email = this.email,
            token = this.token!!,
            username = this.username,
            bio = this.bio,
            image = this.image
        )

    fun toCurrentUserResponse() =
        UserCurrentHttpResponse(
            email = this.email,
            token = this.token,
            username = this.username,
            bio = this.bio,
            image = this.image
        )

    fun toUpdateUserResponse() =
        UserUpdateHttpResponse(
            email = this.email,
            token = this.token,
            username = this.username,
            bio = this.bio,
            image = this.image
        )
}
