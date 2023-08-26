package kr.bread.realworld.controller

import kr.bread.realworld.controller.request.LoginUserHttpRequest
import kr.bread.realworld.controller.request.RegisterUserHttpRequest
import kr.bread.realworld.controller.response.LoginUserHttpResponse
import kr.bread.realworld.controller.response.RegisterUserHttpResponse
import kr.bread.realworld.domain.LoginUserService
import kr.bread.realworld.domain.RegisterUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUserService: RegisterUserService,
    private val loginUserService: LoginUserService,
) {

    @PostMapping("/api/users")
    suspend fun register(
        @RequestBody request: NestedUserHttpData<RegisterUserHttpRequest>,
    ): NestedUserHttpData<RegisterUserHttpResponse> {
        val (username, email, password) = request.user

        return NestedUserHttpData(
            registerUserService.create(
                username = username,
                email = email,
                password = password
            )
                .toRegisterResponse()
        )
    }

    @PostMapping("/api/users/login")
    suspend fun login(
        @RequestBody request: NestedUserHttpData<LoginUserHttpRequest>,
    ): NestedUserHttpData<LoginUserHttpResponse> {
        val (email, password) = request.user

        return NestedUserHttpData(
            loginUserService.login(email, password)
                .toLoginUserResponse()
        )
    }
}