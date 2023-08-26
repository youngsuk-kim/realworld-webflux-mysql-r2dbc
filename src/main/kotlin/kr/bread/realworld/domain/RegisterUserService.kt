package kr.bread.realworld.domain

import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val userRepository: UserRepository,
) {
    suspend fun create(username: String, email: String, password: String): UserResult =
        User.of(username, email, BCryptUtils.hash(password))
            .run {
                with(userRepository.save(this)) {
                    UserResult(
                        email = this.email,
                        username = this.username,
                        bio = this.bio,
                        image = this.image
                    )
                }
            }

}