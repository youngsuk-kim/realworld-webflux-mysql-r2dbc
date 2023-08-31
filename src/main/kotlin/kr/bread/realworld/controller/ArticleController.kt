package kr.bread.realworld.controller

import kr.bread.realworld.controller.EndpointConstants.CREATE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_LIST_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_ONE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.request.ArticleCreateHttpRequest
import kr.bread.realworld.domain.Article
import kr.bread.realworld.domain.ArticleService
import kr.bread.realworld.domain.SingleArticle
import kr.bread.realworld.support.annotation.AuthToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleService: ArticleService,
) {

    @GetMapping(GET_LIST_ARTICLE_ENDPOINT)
    suspend fun getList(
        @AuthToken token: String,
        @RequestParam(required = false) tag: String?,
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) favorited: String?,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0,
    ): MultipleArticleNestedHttpWrapper<SingleArticle> {
        val articles = articleService.getArticles(
            token = token,
            tag = tag,
            author = author,
            favorited = favorited,
            limit = limit,
            offset = offset
        )

        return MultipleArticleNestedHttpWrapper(
            articles, articles.count()
        )

    }

    @GetMapping(GET_ONE_ARTICLE_ENDPOINT)
    suspend fun getOne(@PathVariable slug: String) = SingleArticleNestedHttpWrapper(articleService.getOne(slug))

    @PostMapping(CREATE_ARTICLE_ENDPOINT)
    suspend fun create(
        @AuthToken token: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleCreateHttpRequest>,
    ) = SingleArticleNestedHttpWrapper(articleService.create(token, request.article.toArticleContent()))
}