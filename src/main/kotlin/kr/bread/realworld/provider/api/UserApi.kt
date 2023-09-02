package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.follow.FollowService
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.domain.user.AuthService
import kr.bread.realworld.domain.user.UserRegisterService
import kr.bread.realworld.domain.user.UserUpdateService
import kr.bread.realworld.provider.ApiEndpoints.CURRENT_USER_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.FOLLOW_USER_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.UNFOLLOW_USER_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.UPDATE_USER_ENDPOINT
import kr.bread.realworld.provider.ProfileNestedHttpWrapper
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.UserUpdateHttpRequest
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi(
    private val userFinder: UserFinder,
    private val userUpdateService: UserUpdateService,
    private val followService: FollowService
) {
    @GetMapping(CURRENT_USER_ENDPOINT)
    suspend fun currentUser(
        @Login token: String
    ) = UserNestedHttpWrapper(
        userFinder
            .findByToken(token)
            .toCurrentUserResponse()
    )

    @PutMapping(UPDATE_USER_ENDPOINT)
    suspend fun updateUser(
        @Login token: String,
        @RequestBody request: UserNestedHttpWrapper<UserUpdateHttpRequest>
    ) = UserNestedHttpWrapper(
        userUpdateService.update(
            token = token,
            userContent = request.user.toUserContent()
        ).toUpdateUserResponse()
    )

    @PostMapping(FOLLOW_USER_ENDPOINT)
    suspend fun followUser(
        @Login token: String,
        @PathVariable username: String
    ) = ProfileNestedHttpWrapper(
        followService.follow(token, username).toFollowResponse()
    )

    @DeleteMapping(UNFOLLOW_USER_ENDPOINT)
    suspend fun unfollowUser(
        @Login token: String,
        @PathVariable username: String
    ) = ProfileNestedHttpWrapper(
        followService.unfollow(token, username).toUnFollowResponse()
    )
}
