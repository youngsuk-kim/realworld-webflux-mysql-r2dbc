package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.user.UserService
import kr.bread.realworld.provider.Endpoints.CURRENT_USER_ENDPOINT
import kr.bread.realworld.provider.Endpoints.UPDATE_USER_ENDPOINT
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.UserUpdateHttpRequest
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi(
    private val userService: UserService
) {
    @GetMapping(CURRENT_USER_ENDPOINT)
    suspend fun current(
        @Login token: String
    ) = UserNestedHttpWrapper(
        userService
            .getOne(token)
            .toCurrentUserResponse()
    )

    @PutMapping(UPDATE_USER_ENDPOINT)
    suspend fun update(
        @Login token: String,
        @RequestBody request: UserNestedHttpWrapper<UserUpdateHttpRequest>
    ) = UserNestedHttpWrapper(
        userService.update(
            token = token,
            userContent = request.user.toUserContent()
        ).toUpdateUserResponse()
    )
}
