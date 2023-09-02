package kr.bread.realworld.domain.user

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.JWTProperties
import kr.bread.realworld.support.exception.UserNotFoundException
import kr.bread.realworld.support.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class UserFinder(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties
) {
    suspend fun findById(userId: Long?): User {
        requireNotNull(userId)

        return userRepository.findById(userId)
            ?: throw UserNotFoundException()
    }

    suspend fun findByToken(token: String): User {
        val email = JWTUtils.findEmail(token, jwtProperties)

        return userRepository.findByEmail(email).awaitSingleOrNull()
            ?: throw UserNotFoundException()
    }

    suspend fun findByUsername(username: String) = userRepository.findByUsername(username)?.awaitSingleOrNull() ?: throw UserNotFoundException()

    suspend fun findByEmail(email: String) = userRepository.findByEmail(email).awaitSingleOrNull()
        ?: throw UserNotFoundException()
}
