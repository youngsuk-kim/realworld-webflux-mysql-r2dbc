package kr.bread.realworld.support.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date
import kr.bread.realworld.config.JWTProperties

object JWTUtils {

    fun createToken(claim: JWTClaim, properties: JWTProperties) =
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

    fun decode(token: String, secret: String, issuer: String): DecodedJWT {
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