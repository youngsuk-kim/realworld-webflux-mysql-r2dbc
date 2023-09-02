package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.user.AuthService
import kr.bread.realworld.provider.ApiEndpoints
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthApi(
    private val authService: AuthService
) {
    @PostMapping(ApiEndpoints.REGISTER_ENDPOINT)
    suspend fun register(
        @RequestBody
        request: UserNestedHttpWrapper<AuthRegisterHttpRequest>
    ) = UserNestedHttpWrapper(
        authService
            .create(
                username = request.user.username,
                email = request.user.email,
                password = request.user.password
            )
            .toRegisterResponse()
    )

    @PostMapping(ApiEndpoints.LOGIN_ENDPOINT)
    suspend fun login(
        @RequestBody
        request: UserNestedHttpWrapper<AuthLoginHttpRequest>
    ) = UserNestedHttpWrapper(
        authService
            .login(request.user.email, request.user.password)
            .toLoginUserResponse()
    )
}