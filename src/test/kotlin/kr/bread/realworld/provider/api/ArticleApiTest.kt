package kr.bread.realworld.provider.api

import kr.bread.realworld.provider.Endpoints
import kr.bread.realworld.provider.Endpoints.DELETE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_ARTICLE_FEED_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_LIST_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_ONE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.LOGIN_ENDPOINT
import kr.bread.realworld.provider.Endpoints.UPDATE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.MultipleArticleNestedHttpWrapper
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.ArticleUpdateHttpRequest
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import kr.bread.realworld.provider.response.ArticleHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.returnResult

class ArticleApiTest: AbstractIntegrationTest() {

    @BeforeEach
    fun setUp() {
        register()
        login()
    }

    @Test
    fun `create article`() {
        val result = createArticle()

        assertThat(result).isNotNull
        assertThat(result!!.article.slug).isEqualTo("test-title")
        assertThat(result.article.title).isEqualTo("test-title")
        assertThat(result.article.description).isEqualTo("test-description")
        assertThat(result.article.body).isEqualTo("test-body")
        assertThat(result.article.tagList).isEqualTo(setOf("test-tag-1", "test-tag-2"))
    }

    @Test
    fun `get one article`() {
        val createArticleResult = createArticle()

        val result = webTestClient
            .get()
            .uri(GET_ONE_ARTICLE_ENDPOINT.replace("{slug}", createArticleResult!!.article.slug!!))
            .accept(APPLICATION_JSON)
            .exchange()
            .returnResult<SingleArticleNestedHttpWrapper<ArticleHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.article.slug).isEqualTo("test-title")
        assertThat(result.article.title).isEqualTo("test-title")
        assertThat(result.article.description).isEqualTo("test-description")
        assertThat(result.article.body).isEqualTo("test-body")
        assertThat(result.article.tagList).isEqualTo(setOf("test-tag-1", "test-tag-2"))
    }

    @Test
    fun `get all article`() {
        createArticle()

        val result = webTestClient
            .get()
            .uri(GET_LIST_ARTICLE_ENDPOINT)
            .accept(APPLICATION_JSON)
            .exchange()
            .returnResult<MultipleArticleNestedHttpWrapper<ArticleHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.articles.size).isEqualTo(1)
        assertThat(result.articles[0].slug).isEqualTo("test-title")
        assertThat(result.articles[0].title).isEqualTo("test-title")
        assertThat(result.articles[0].description).isEqualTo("test-description")
        assertThat(result.articles[0].body).isEqualTo("test-body")
        assertThat(result.articles[0].tagList).isEqualTo(setOf("test-tag-1", "test-tag-2"))
    }

    @Test
    fun `get feed`() {
        createArticle()

        val result = webTestClient
            .get()
            .uri(GET_ARTICLE_FEED_ENDPOINT)
            .accept(APPLICATION_JSON)
            .exchange()
            .returnResult<MultipleArticleNestedHttpWrapper<ArticleHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.articles.size).isEqualTo(1)
        assertThat(result.articles[0].slug).isEqualTo("test-title")
        assertThat(result.articles[0].title).isEqualTo("test-title")
        assertThat(result.articles[0].description).isEqualTo("test-description")
        assertThat(result.articles[0].body).isEqualTo("test-body")
        assertThat(result.articles[0].tagList).isEqualTo(setOf("test-tag-1", "test-tag-2"))
    }

    @Test
    fun `update article`() {
        val createArticleRequest = createArticle()

        val request = ArticleUpdateHttpRequest(
            title = "update-title",
            description = "update-description",
            body = "update-body",
            tagList = setOf("update-tag-1", "update-tag-2")
        )

        val wrappedRequest = SingleArticleNestedHttpWrapper(request)

        val result = webTestClient
            .put()
            .uri(UPDATE_ARTICLE_ENDPOINT.replace("{slug}", createArticleRequest!!.article.slug!!))
            .bodyValue(wrappedRequest)
            .accept(APPLICATION_JSON)
            .exchange()
            .returnResult<SingleArticleNestedHttpWrapper<ArticleHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(result).isNotNull
        assertThat(result!!.article.slug).isEqualTo("update-title")
        assertThat(result.article.title).isEqualTo("update-title")
        assertThat(result.article.description).isEqualTo("update-description")
        assertThat(result.article.body).isEqualTo("update-body")
        assertThat(result.article.tagList).isEqualTo(setOf("update-tag-1", "update-tag-2"))
    }

    @Test
    fun `delete article`() {
        val createArticleRequest = createArticle()

        webTestClient
            .delete()
            .uri(DELETE_ARTICLE_ENDPOINT.replace("{slug}", createArticleRequest!!.article.slug!!))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
    }

    private fun login() {
        val requestLogin = AuthLoginHttpRequest(
            email = "test-email",
            password = "test-password"
        )

        val wrappedLoginRequest = UserNestedHttpWrapper(requestLogin)

        val returnResult1 = webTestClient
            .post()
            .uri(LOGIN_ENDPOINT)
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
            .returnResult<SingleArticleNestedHttpWrapper<ArticleHttpResponse>>()
            .responseBody.blockFirst()
    }
}