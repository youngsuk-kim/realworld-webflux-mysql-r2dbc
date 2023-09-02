package kr.bread.realworld.provider.request

import kr.bread.realworld.domain.user.UserContent

data class UserUpdateHttpRequest(
    val email: String?,
    val bio: String?,
    val image: String?
) {
    fun toUserContent(): UserContent {
        return UserContent(
            email = this.email,
            bio = this.bio,
            image = this.image
        )
    }
}
