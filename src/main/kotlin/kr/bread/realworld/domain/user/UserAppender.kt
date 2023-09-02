package kr.bread.realworld.domain.user

import kr.bread.realworld.infra.UserRepository
import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository
) {
    suspend fun save(user: User) = userRepository.save(user)
}
