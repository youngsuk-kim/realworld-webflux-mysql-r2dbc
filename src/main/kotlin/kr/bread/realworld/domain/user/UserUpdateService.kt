package kr.bread.realworld.domain.user

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.EmailAlreadyRegisterException
import kr.bread.realworld.support.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUpdateService(
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
}
