package kr.bread.realworld.support.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.time.Instant
import kr.bread.realworld.support.JWTProperties
import kr.bread.realworld.support.exception.InvalidJwtTokenException
import java.util.Date

private const val EMAIL = "email"

private const val EXPIRE_TIME = 3600 * 24 // 1 hour * 24

object JWTUtils {

    fun createToken(claim: JWTClaim, properties: JWTProperties): String =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(EXPIRE_TIME.toLong())))
            .withClaim(EMAIL, claim.email)
            .sign(Algorithm.HMAC256(properties.secret))

    fun findEmail(token: String, properties: JWTProperties): String {
        return decode(token, properties.secret, properties.issuer)
            .claims[EMAIL]?.asString() ?: throw InvalidJwtTokenException()
    }

    private fun decode(token: String, secret: String, issuer: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secret)

        val verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()

        return verifier.verify(token)
    }
}

data class JWTClaim(
    val email: String
)
