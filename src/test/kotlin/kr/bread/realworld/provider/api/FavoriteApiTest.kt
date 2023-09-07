package kr.bread.realworld.provider.api

import kr.bread.realworld.provider.Endpoints
import kr.bread.realworld.provider.ProfileNestedHttpWrapper
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import kr.bread.realworld.provider.response.ArticleHttpResponse
import kr.bread.realworld.provider.response.UserFollowHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.returnResult

class FavoriteApiTest: AbstractIntegrationTest() {

    @BeforeEach
    fun setUp() {
        register()
        login()
        createArticle()
    }

    @Test
    fun `follow user`() {
        val result = webTestClient
            .post()
            .uri(Endpoints.FOLLOW_USER_ENDPOINT.replace("{username}", "test2-username"))
            .exchange()
            .returnResult<ProfileNestedHttpWrapper<UserFollowHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.profile.username).isEqualTo("test2-username")
        assertThat(result.profile.following).isTrue()
    }

    @Test
    fun `unfollow user`() {
        webTestClient
            .post()
            .uri(Endpoints.FOLLOW_USER_ENDPOINT.replace("{username}", "test2-username"))
            .exchange()
            .returnResult<ProfileNestedHttpWrapper<UserFollowHttpResponse>>()
            .responseBody.blockFirst()

        val result = webTestClient
            .delete()
            .uri(Endpoints.UNFOLLOW_USER_ENDPOINT.replace("{username}", "test2-username"))
            .exchange()
            .returnResult<ProfileNestedHttpWrapper<UserFollowHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.profile.username).isEqualTo("test2-username")
        assertThat(result.profile.following).isFalse()
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
            ) // 공통 헤더 추가 가능
            .build()
    }

    private fun register() {
        val request = AuthRegisterHttpRequest(
            username = "test-username",
            email = "test-email",
            password = "test-password"
        )
        val wrappedRequest = UserNestedHttpWrapper(request)

        val request2 = AuthRegisterHttpRequest(
            username = "test2-username",
            email = "test2-email",
            password = "test2-password"
        )
        val wrappedRequest2 = UserNestedHttpWrapper(request2)

        webTestClient
            .post()
            .uri(Endpoints.REGISTER_ENDPOINT)
            .bodyValue(wrappedRequest)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserRegisterHttpResponse>>()

        webTestClient
            .post()
            .uri(Endpoints.REGISTER_ENDPOINT)
            .bodyValue(wrappedRequest2)
            .exchange()
            .returnResult<UserNestedHttpWrapper<UserRegisterHttpResponse>>()
    }

    private fun createArticle(): SingleArticleNestedHttpWrapper<ArticleHttpResponse>? {
        val request = ArticleCreateHttpRequest(
            title = "test-title",
            description = "test-description",
            body = "test-body",
            tagList = setOf("test-tag-1", "test-tag-2")
        )

        val wrappedRequest = SingleArticleNestedHttpWrapper(request)


        return webTestClient
            .post()
            .uri(Endpoints.CREATE_ARTICLE_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue((wrappedRequest))
            .exchange()
            .returnResult<SingleArticleNestedHttpWrapper<ArticleHttpResponse>>().responseBody.blockFirst()
    }

}