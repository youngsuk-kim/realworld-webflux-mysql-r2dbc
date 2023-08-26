package kr.bread.realworld.controller

import kr.bread.realworld.controller.EndpointConstants.CURRENT_USER_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.LOGIN_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.REGISTER_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.UPDATE_USER_ENDPOINT
import kr.bread.realworld.controller.request.UserLoginHttpRequest
import kr.bread.realworld.controller.request.UserRegisterHttpRequest
import kr.bread.realworld.controller.request.UserUpdateHttpRequest
import kr.bread.realworld.controller.response.UserLoginHttpResponse
import kr.bread.realworld.controller.response.UserRegisterHttpResponse
import kr.bread.realworld.domain.UserFindService
import kr.bread.realworld.domain.UserLoginService
import kr.bread.realworld.domain.UserRegisterService
import kr.bread.realworld.domain.UserUpdateService
import kr.bread.realworld.support.annotation.AuthToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userRegisterService: UserRegisterService,
    private val userLoginService: UserLoginService,
    private val userFindService: UserFindService,
    private val userUpdateService: UserUpdateService,
) {

    @PostMapping(REGISTER_ENDPOINT)
    suspend fun register(
        @RequestBody
        request: UserNestedHttpWrapper<UserRegisterHttpRequest>,
    ) = UserNestedHttpWrapper(
        userRegisterService
            .create(
                username = request.user.username,
                email = request.user.email,
                password = request.user.password
            )
            .toRegisterResponse()
    )

    @PostMapping(LOGIN_ENDPOINT)
    suspend fun login(
        @RequestBody
        request: UserNestedHttpWrapper<UserLoginHttpRequest>,
    ) = UserNestedHttpWrapper(
        userLoginService
            .login(request.user.email, request.user.password)
            .toLoginUserResponse()
    )

    @GetMapping(CURRENT_USER_ENDPOINT)
    suspend fun currentUser(
        @AuthToken token: String,
    ) = UserNestedHttpWrapper(
        userFindService
            .findByToken(token)
            .toCurrentUserResponse()
    )

    @PutMapping(UPDATE_USER_ENDPOINT)
    suspend fun updateUser(
        @AuthToken token: String,
        @RequestBody request: UserNestedHttpWrapper<UserUpdateHttpRequest>,
    ) = UserNestedHttpWrapper(
        userUpdateService.update(
            token = token,
            email = request.user.email,
            bio = request.user.bio,
            image = request.user.image
        ).toUpdateUserResponse()
    )

}