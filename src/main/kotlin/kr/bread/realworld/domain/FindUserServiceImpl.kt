package kr.bread.realworld.domain

import kr.bread.realworld.infra.ReactiveUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FindUserServiceImpl(
    private val userRepository: ReactiveUserRepository
): FindUserService {
    override fun findById(userId: Long): Mono<User> {
        return userRepository.findById(userId)
    }
}