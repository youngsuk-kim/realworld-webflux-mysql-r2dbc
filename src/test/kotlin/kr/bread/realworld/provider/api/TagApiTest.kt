package kr.bread.realworld.provider.api

import kr.bread.realworld.provider.Endpoints
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.TagNestedHttpWrapper
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import kr.bread.realworld.provider.response.ArticleHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.returnResult

class TagApiTest: AbstractIntegrationTest() {

    @BeforeEach
    fun setUp() {
        register()
        login()
        createArticle()
    }

    @Test
    fun `get all tag`() {
        val result = webTestClient
            .get()
            .uri(Endpoints.GET_TAGS_ENDPOINT)
            .exchange()
            .returnResult<TagNestedHttpWrapper<List<String>>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.tags).isNotEmpty
        assertThat(result.tags[0]).isEqualTo("test-tag-1")
        assertThat(result.tags[1]).isEqualTo("test-tag-2")
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

        webTestClient
            .post()
            .uri(Endpoints.REGISTER_ENDPOINT)
            .bodyValue(wrappedRequest)
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