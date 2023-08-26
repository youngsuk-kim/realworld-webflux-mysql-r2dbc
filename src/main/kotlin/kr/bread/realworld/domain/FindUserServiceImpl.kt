package kr.bread.realworld.domain

import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class FindUserServiceImpl(
    private val userRepository: UserRepository
): FindUserService {
    override suspend fun findById(userId: Long): UserResult {
        val user = userRepository.findById(userId)
            ?: throw UserNotFoundException()

        return UserResult(
            email = user.email,
            username = user.username,
            bio = user.bio,
            image = user.image
        )
    }
}