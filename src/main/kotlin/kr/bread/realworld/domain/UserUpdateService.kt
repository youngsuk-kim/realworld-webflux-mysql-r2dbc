package kr.bread.realworld.domain

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.EmailAlreadyRegisterException
import kr.bread.realworld.support.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUpdateService(
    private val userFindService: UserFindService,
    private val userRepository: UserRepository
) {

    @Transactional
    suspend fun update(token: String, email: String?, bio: String?, image: String?): UserResult {
        val userResult = userFindService.findByToken(token)
        if (userResult.email == email) throw EmailAlreadyRegisterException()

        val user = userRepository.findByEmail(userResult.email)
            .awaitFirstOrNull() ?: throw UserNotFoundException()
        user.update(email, bio, image)

        val saveUser = userRepository.save(user)

        return UserResult(
            id = saveUser.id!!,
            email = saveUser.email,
            username = saveUser.username,
            bio = saveUser.bio,
            image = saveUser.image
        )
    }
}