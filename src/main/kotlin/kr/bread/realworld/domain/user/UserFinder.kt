package kr.bread.realworld.domain.user

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.support.JWTProperties
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.UserNotFoundException
import kr.bread.realworld.support.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class UserFinder(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties
) {

    private val log = KotlinLogging.logger {}

    suspend fun findById(userId: Long?): UserResult {
        requireNotNull(userId)

        val user = userRepository.findById(userId)
            ?: throw UserNotFoundException()

        return UserResult.of(user)
    }

    suspend fun findByToken(token: String): UserResult {
        val email = JWTUtils.findEmail(token, jwtProperties)

        val user = (userRepository.findByEmail(email).awaitSingleOrNull()
            ?: throw UserNotFoundException())

        return UserResult.of(user)
    }

    suspend fun findByUsername(username: String): UserResult {
        val user = userRepository.findByUsername(username)?.awaitSingleOrNull()

        return UserResult.of(user)
    }

    suspend fun findUserById(userId: Long): User? {
        return userRepository.findById(userId)
    }

    suspend fun findByEmail(email: String): User {
        return userRepository.findByEmail(email).awaitSingleOrNull()
            ?: throw UserNotFoundException()
    }
}
