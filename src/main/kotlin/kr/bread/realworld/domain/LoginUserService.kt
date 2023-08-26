package kr.bread.realworld.domain

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.bread.realworld.config.JWTProperties
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.PasswordNotMatchedException
import kr.bread.realworld.support.exception.UserNotFoundException
import kr.bread.realworld.support.utils.BCryptUtils
import kr.bread.realworld.support.utils.JWTClaim
import kr.bread.realworld.support.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
) {

    suspend fun login(email: String, password: String): UserResult {
        with(userRepository.findByEmail(email).awaitFirstOrNull() ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(password, this.password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                email = email,
                username = username
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            return UserResult(
                email = this.email,
                username = this.username,
                bio = this.bio,
                image = this.image,
                token = token
            )
        }
    }
}