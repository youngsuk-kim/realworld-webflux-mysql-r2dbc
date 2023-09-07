package kr.bread.realworld.domain.user

import kr.bread.realworld.provider.response.UserCurrentHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import kr.bread.realworld.provider.response.UserUpdateHttpResponse

data class UserResult(
    val id: Long,
    val email: String,
    val token: String? = null,
    val username: String,
    val bio: String?,
    val image: String? = null
) {

    companion object {
        fun of(user: User?, token: String?): UserResult {
            return UserResult(
                id = user?.id!!,
                email = user.email,
                token = token,
                username = user.username,
                bio = user.bio,
                image = user.image
            )
        }

        fun of(user: User?): UserResult {
            return UserResult(
                id = user?.id!!,
                email = user.email,
                username = user.username,
                bio = user.bio
            )
        }

        fun toUser(userResult: UserResult): User {
            return User(
                username = userResult.username,
                email = userResult.email,
                bio = userResult.bio,
                image = userResult.image,
                id = userResult.id,
                password = ""
            )
        }
    }

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
