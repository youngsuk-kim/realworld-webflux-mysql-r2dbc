package kr.bread.realworld.domain.user

import kr.bread.realworld.infra.UserRepository
import kr.bread.realworld.support.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserRegisterService(
    private val userRepository: UserRepository
) {
}
