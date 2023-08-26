package kr.bread.realworld.controller

import kr.bread.realworld.controller.request.UserLoginHttpRequest
import kr.bread.realworld.controller.request.UserRegisterHttpRequest
import kr.bread.realworld.controller.response.UserLoginHttpResponse
import kr.bread.realworld.controller.response.UserRegisterHttpResponse
import kr.bread.realworld.domain.UserLoginService
import kr.bread.realworld.domain.UserRegisterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userRegisterService: UserRegisterService,
    private val userLoginService: UserLoginService,
) {

    @PostMapping("/api/users")
    suspend fun register(
        @RequestBody request: UserNestedHttpWrapper<UserRegisterHttpRequest>,
    ): UserNestedHttpWrapper<UserRegisterHttpResponse> {
        val (username, email, password) = request.user

        return UserNestedHttpWrapper(
            userRegisterService.create(
                username = username,
                email = email,
                password = password
            )
                .toRegisterResponse()
        )
    }

    @PostMapping("/api/users/login")
    suspend fun login(
        @RequestBody request: UserNestedHttpWrapper<UserLoginHttpRequest>,
    ): UserNestedHttpWrapper<UserLoginHttpResponse> {
        val (email, password) = request.user

        return UserNestedHttpWrapper(
            userLoginService.login(email, password)
                .toLoginUserResponse()
        )
    }
}