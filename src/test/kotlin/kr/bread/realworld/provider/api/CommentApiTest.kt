package kr.bread.realworld.provider.api

import kotlinx.coroutines.runBlocking
import kr.bread.realworld.provider.CommentNestedHttpWrapper
import kr.bread.realworld.provider.CommentsNestedHttpWrapper
import kr.bread.realworld.provider.Endpoints
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.UserNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.AuthLoginHttpRequest
import kr.bread.realworld.provider.request.AuthRegisterHttpRequest
import kr.bread.realworld.provider.request.CommentCreateHttpRequest
import kr.bread.realworld.provider.response.ArticleHttpResponse
import kr.bread.realworld.provider.response.CommentHttpResponse
import kr.bread.realworld.provider.response.UserLoginHttpResponse
import kr.bread.realworld.provider.response.UserRegisterHttpResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.returnResult

class CommentApiTest: AbstractIntegrationTest() {

    @BeforeEach
    fun setUp() {
        register()
        login()
    }

    @Test
    fun `create comment`() {
        val createArticle = createArticle()

        val createCommentRequest = CommentCreateHttpRequest(body = "test-comment")
        val commentNestedHttpWrapper = CommentNestedHttpWrapper(createCommentRequest)

        val comment = webTestClient
            .post()
            .uri(Endpoints.CREATE_COMMENT_ENDPOINT.replace("{slug}", createArticle!!.article.slug!!))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue((commentNestedHttpWrapper))
            .exchange()
            .returnResult<CommentNestedHttpWrapper<CommentHttpResponse>>()
            .responseBody.blockFirst()

        assertThat(comment).isNotNull
        assertThat(comment!!.comment.body).isEqualTo("test-comment")
    }

    @Test
    fun `get all comment`() {
        val createArticle = createArticle()

        val createCommentRequest = CommentCreateHttpRequest(body = "test-comment")
        val commentNestedHttpWrapper = CommentNestedHttpWrapper(createCommentRequest)

        webTestClient
            .post()
            .uri(Endpoints.CREATE_COMMENT_ENDPOINT.replace("{slug}", createArticle!!.article.slug!!))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue((commentNestedHttpWrapper))
            .exchange()
            .returnResult<CommentNestedHttpWrapper<CommentHttpResponse>>()
            .responseBody.blockFirst()

        val comments = webTestClient
            .get()
            .uri(Endpoints.GET_COMMENTS_ENDPOINT.replace("{slug}", createArticle.article.slug!!))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult<CommentsNestedHttpWrapper<List<CommentHttpResponse>>>()
            .responseBody.blockFirst()

        assertThat(comments).isNotNull
        assertThat(comments!!.comments[0].body).isEqualTo("test-comment")
    }

    @Test
    fun `delete comment`() {
        val createArticle = createArticle()

        val createCommentRequest = CommentCreateHttpRequest(body = "test-comment")
        val commentNestedHttpWrapper = CommentNestedHttpWrapper(createCommentRequest)

        webTestClient
            .post()
            .uri(Endpoints.CREATE_COMMENT_ENDPOINT.replace("{slug}", createArticle!!.article.slug!!))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue((commentNestedHttpWrapper))
            .exchange()
            .returnResult<CommentNestedHttpWrapper<CommentHttpResponse>>()
            .responseBody.blockFirst()

        val comments = webTestClient
            .get()
            .uri(Endpoints.GET_COMMENTS_ENDPOINT.replace("{slug}", createArticle.article.slug!!))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult<CommentsNestedHttpWrapper<List<CommentHttpResponse>>>()
            .responseBody.blockFirst()

        webTestClient
            .delete()
            .uri(
                Endpoints.DELETE_COMMENT_ENDPOINT
                    .replace("{slug}", createArticle.article.slug!!)
                    .replace("{id}", comments!!.comments[0].id.toString())
            )
            .accept(MediaType.APPLICATION_JSON)
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