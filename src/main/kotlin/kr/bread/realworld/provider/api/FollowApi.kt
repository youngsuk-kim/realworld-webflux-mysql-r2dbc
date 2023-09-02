package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.follow.FollowService
import kr.bread.realworld.provider.Endpoints.FOLLOW_USER_ENDPOINT
import kr.bread.realworld.provider.Endpoints.UNFOLLOW_USER_ENDPOINT
import kr.bread.realworld.provider.ProfileNestedHttpWrapper
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowApi(
    private val followService: FollowService
) {
    @PostMapping(FOLLOW_USER_ENDPOINT)
    suspend fun follow(
        @Login token: String,
        @PathVariable username: String
    ) = ProfileNestedHttpWrapper(
        followService.follow(token, username).toFollowResponse()
    )

    @DeleteMapping(UNFOLLOW_USER_ENDPOINT)
    suspend fun unfollow(
        @Login token: String,
        @PathVariable username: String
    ) = ProfileNestedHttpWrapper(
        followService.unfollow(token, username).toUnFollowResponse()
    )
}
