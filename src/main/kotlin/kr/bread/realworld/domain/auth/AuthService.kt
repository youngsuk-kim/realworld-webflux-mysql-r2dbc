package kr.bread.realworld.domain.auth

import kr.bread.realworld.domain.user.User
import kr.bread.realworld.domain.user.UserAppender
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.domain.user.UserResult
import kr.bread.realworld.support.JWTProperties
import kr.bread.realworld.support.exception.PasswordNotMatchedException
import kr.bread.realworld.support.utils.BCryptUtils.hash
import kr.bread.realworld.support.utils.BCryptUtils.verify
import kr.bread.realworld.support.utils.JWTClaim
import kr.bread.realworld.support.utils.JWTUtils.createToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userFinder: UserFinder,
    private val userAppender: UserAppender,
    private val jwtProperties: JWTProperties
) {

    suspend fun login(email: String, password: String): UserResult {
        val user = userFinder.findByEmail(email)
        if (!verify(password, user.password)) throw PasswordNotMatchedException()

        val token = createToken(JWTClaim(email), jwtProperties)

        return UserResult.of(user, token)
    }

    suspend fun create(username: String, email: String, password: String): UserResult {
        val user = User.of(username, email, hash(password))
        return UserResult.of(userAppender.save(user))
    }
}
