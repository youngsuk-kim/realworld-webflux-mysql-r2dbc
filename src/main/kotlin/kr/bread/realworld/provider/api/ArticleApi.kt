package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.article.ArticleResult
import kr.bread.realworld.domain.article.ArticleService
import kr.bread.realworld.provider.ApiEndpoints.CREATE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.DELETE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.GET_ARTICLE_FEED_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.GET_LIST_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.GET_ONE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.UPDATE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.MultipleArticleNestedHttpWrapper
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.ArticleUpdateHttpRequest
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleApi(
    private val articleService: ArticleService
) {

    @PostMapping(CREATE_ARTICLE_ENDPOINT)
    suspend fun create(
        @Login token: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleCreateHttpRequest>
    ) = SingleArticleNestedHttpWrapper(articleService.create(token, request.article.toArticleContent()))

    @GetMapping(GET_ONE_ARTICLE_ENDPOINT)
    suspend fun getOne(@PathVariable slug: String) = SingleArticleNestedHttpWrapper(articleService.getOne(slug))

    @GetMapping(GET_LIST_ARTICLE_ENDPOINT)
    suspend fun getList(
        @Login token: String,
        @RequestParam(required = false) tag: String?,
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) favorited: String?,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0
    ): MultipleArticleNestedHttpWrapper<ArticleResult> {
        val articles = articleService.getMany(
            token = token,
            tag = tag,
            author = author,
            favorited = favorited,
            limit = limit,
            offset = offset
        )

        return MultipleArticleNestedHttpWrapper(
            articles,
            articles.count()
        )
    }

    @GetMapping(GET_ARTICLE_FEED_ENDPOINT)
    suspend fun getFeed(
        @Login token: String,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0
    ): MultipleArticleNestedHttpWrapper<ArticleResult> {
        val feed = articleService.getFeed(token, limit, offset)

        return MultipleArticleNestedHttpWrapper(
            feed,
            feed.count()
        )
    }

    @PutMapping(UPDATE_ARTICLE_ENDPOINT)
    suspend fun update(
        @Login token: String,
        @PathVariable slug: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleUpdateHttpRequest>
    ) {
        val (title, description, body) = request.article
        SingleArticleNestedHttpWrapper(articleService.update(slug, title, description, body))
    }

    @DeleteMapping(DELETE_ARTICLE_ENDPOINT)
    suspend fun delete(
        @Login token: String,
        @PathVariable slug: String
    ): SingleArticleNestedHttpWrapper<ArticleResult> {
        return SingleArticleNestedHttpWrapper(articleService.delete(slug))
    }
}
