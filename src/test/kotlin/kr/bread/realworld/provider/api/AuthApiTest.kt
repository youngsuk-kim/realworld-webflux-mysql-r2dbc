package kr.bread.realworld.provider.api

import kr.bread.realworld.provider.Endpoints.REGISTER_ENDPOINT
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import org.junit.jupiter.api.Test

class AuthApiTest: AbstractIntegrationTest() {

    @Test
    fun `register success`() {
        val request = AuthRegisterHttpRequest(
            username = "test-username",
            email = "test-email",
            password = "test-password"
        )
        val wrappedRequest = UserNestedHttpWrapper(request)

        webTestClient
            .post()
            .uri(REGISTER_ENDPOINT)
            .bodyValue(wrappedRequest)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun `login success`() {

    }
}