package kr.bread.realworld.infra

import kr.bread.realworld.domain.user.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : CoroutineCrudRepository<User, Long> {
    fun findByEmail(email: String): Mono<User>
    fun findByUsername(username: String?): Mono<User>?
}
