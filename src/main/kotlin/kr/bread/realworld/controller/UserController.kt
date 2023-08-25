package kr.bread.realworld.controller

import kr.bread.realworld.domain.User
import kr.bread.realworld.domain.UserRegisterService
import kr.bread.realworld.controller.request.CreateUserHttpRequest
import kr.bread.realworld.controller.request.NestedUserHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class UserController(
    private val userRegisterService: UserRegisterService
) {

    @PostMapping("/api/users")
    fun register(
        @RequestBody
        request: NestedUserHttpRequest<CreateUserHttpRequest>
    ): Mono<User> {
        val (username, email, password) = request.user
        return userRegisterService.create(username, email, password)
    }
}