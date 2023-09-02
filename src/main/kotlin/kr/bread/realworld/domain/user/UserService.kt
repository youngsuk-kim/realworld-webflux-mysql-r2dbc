package kr.bread.realworld.domain.user

import kr.bread.realworld.support.exception.EmailAlreadyRegisterException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userFinder: UserFinder,
    private val userAppender: UserAppender
) {
    suspend fun update(token: String, userContent: UserContent): UserResult {
        val userResult = userFinder.findByToken(token)
        if (userResult.email == userContent.email) throw EmailAlreadyRegisterException()

        val user = userFinder.findByEmail(userResult.email)
            .update(userContent.email, userContent.bio, userContent.image)
            .run { userAppender.save(this) }

        return UserResult.of(user, token)
    }

    suspend fun getOne(token: String) = UserResult.of(userFinder.findByToken(token))
}
