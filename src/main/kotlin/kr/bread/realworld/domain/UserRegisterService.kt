package kr.bread.realworld.domain

import kr.bread.realworld.infra.ReactiveUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserRegisterService(
    private val userRepository: ReactiveUserRepository,
) {
    fun create(username: String, email: String, password: String): Mono<User> =
        User.of(username, email, password)
            .run { userRepository.save(this) }

}