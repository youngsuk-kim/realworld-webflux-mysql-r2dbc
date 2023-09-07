package kr.bread.realworld.provider.api

import kr.bread.realworld.provider.Endpoints
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import kr.bread.realworld.provider.request.UserUpdateHttpRequest
import kr.bread.realworld.provider.response.UserCurrentHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import kr.bread.realworld.provider.response.UserUpdateHttpResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.returnResult

class UserApiTest: AbstractIntegrationTest() {

    @BeforeEach
    fun setUp() {
        register()
        login()
    }

    @Test
    fun `get current user`() {
        val result = webTestClient
            .get()
            .uri(Endpoints.CURRENT_USER_ENDPOINT)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserCurrentHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.user.email).isEqualTo("test-email")
        assertThat(result.user.username).isEqualTo("test-username")
    }

    @Test
    fun `update user`() {
        val userUpdateHttpRequest = UserUpdateHttpRequest(
            email = "update-email",
            bio = "update-bio",
            image = "update-image"
        )

        val wrappedRequest = UserNestedHttpWrapper(userUpdateHttpRequest)

        val result = webTestClient
            .put()
            .uri(Endpoints.UPDATE_USER_ENDPOINT)
            .bodyValue(wrappedRequest)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserUpdateHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.user.email).isEqualTo("update-email")
        assertThat(result.user.bio).isEqualTo("update-bio")
        assertThat(result.user.image).isEqualTo("update-image")
    }

    private fun login() {
        val requestLogin = AuthLoginHttpRequest(
            email = "test-email",
            password = "test-password"
        )

        val wrappedLoginRequest = UserNestedHttpWrapper(requestLogin)

        val returnResult1 = webTestClient
            .post()
            .uri(Endpoints.LOGIN_ENDPOINT)
            .bodyValue(wrappedLoginRequest)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserLoginHttpResponse>>()

        webTestClient = webTestClient.mutate()
            .defaultHeader(
                "Authorization",
                "Token ${returnResult1.responseBody.blockFirst()!!.user.token}"
            )
            .build()
    }

    private fun register() {
        val request = AuthRegisterHttpRequest(
            username = "test-username",
            email = "test-email",
            password = "test-password"
        )
        val wrappedRequest = UserNestedHttpWrapper(request)

        webTestClient
            .post()
            .uri(Endpoints.REGISTER_ENDPOINT)
            .bodyValue(wrappedRequest)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserRegisterHttpResponse>>()
    }

}