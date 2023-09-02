package kr.bread.realworld.support.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import kr.bread.realworld.support.JWTProperties
import java.util.Date
import kr.bread.realworld.support.exception.InvalidJwtTokenException

object JWTUtils {

    fun createToken(claim: JWTClaim, properties: JWTProperties): String =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("email", claim.email)
            .withClaim("username", claim.username)
            .withClaim("bio", claim.bio)
            .withClaim("image", claim.image)
            .sign(Algorithm.HMAC256(properties.secret))

    fun findEmail(token: String, properties: JWTProperties): String {
        val email = decode(token, properties.secret, properties.issuer)
            .claims["email"]?.asString() ?: throw InvalidJwtTokenException()

        return email
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
    val email: String,
    val username: String,
    val image: String = "",
    val bio: String = "",
)
