package kr.bread.realworld.domain.user

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.config.JWTProperties
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.InvalidJwtTokenException
import kr.bread.realworld.support.exception.UserNotFoundException
import kr.bread.realworld.support.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class UserFindServiceImpl(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
) : UserFindService {

    private val log = KotlinLogging.logger {}

    override suspend fun findById(userId: Long?): UserResult {
        val user = userRepository
            .findById(userId!!) ?: throw UserNotFoundException()

        return UserResult(
            id = user.id!!,
            email = user.email,
            username = user.username,
            bio = user.bio,
            image = user.image,
        )
    }

    override suspend fun findByToken(token: String): UserResult {

        val email = JWTUtils
            .decode(token, jwtProperties.secret, jwtProperties.issuer)
            .claims["email"]?.asString()
            ?: throw InvalidJwtTokenException()

        log.info { "Login user email : $email" }

        return with(userRepository.findByEmail(email).awaitSingleOrNull()
            ?: throw UserNotFoundException()) {
            UserResult(
                id = this.id!!,
                email = this.email,
                username = this.username,
                bio = this.bio,
                image = this.image,
            )
        }
    }

    override suspend fun findByUsername(username: String): UserResult {
        val user = userRepository.findByUsername(username)?.awaitSingleOrNull()

        return UserResult(
            id = user?.id!!,
            email = user.email,
            username = user.username,
            bio = user.bio,
            image = user.image
        )
    }

    override suspend fun findUserById(userId: Long): User? {
        return userRepository.findById(userId)
    }
}