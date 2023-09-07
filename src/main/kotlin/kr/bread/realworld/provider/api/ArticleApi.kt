package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.article.ArticleFilterCondition
import kr.bread.realworld.domain.article.ArticlePaging
import kr.bread.realworld.domain.article.ArticleService
import kr.bread.realworld.domain.article.ArticleUpdateContent
import kr.bread.realworld.provider.Endpoints.CREATE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.DELETE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_ARTICLE_FEED_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_LIST_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.GET_ONE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.Endpoints.UPDATE_ARTICLE_ENDPOINT
import kr.bread.realworld.provider.MultipleArticleNestedHttpWrapper
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.provider.request.ArticleCreateHttpRequest
import kr.bread.realworld.provider.request.ArticleUpdateHttpRequest
import kr.bread.realworld.provider.response.ArticleHttpResponse
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
    ) = SingleArticleNestedHttpWrapper(
        articleService.create(token, request.article.toArticleContent()).toArticleHttpResponse()
    )

    @GetMapping(GET_ONE_ARTICLE_ENDPOINT)
    suspend fun getOne(@PathVariable slug: String) =
        SingleArticleNestedHttpWrapper(articleService.getOne(slug = slug).toArticleHttpResponse())

    @GetMapping(GET_LIST_ARTICLE_ENDPOINT)
    suspend fun getAll(
        @Login token: String,
        @RequestParam(required = false) tag: String?,
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) favorited: String?,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0
    ): MultipleArticleNestedHttpWrapper<ArticleHttpResponse> {
        val articles = articleService.getAll(
            token = token,
            paging = ArticlePaging(limit, offset),
            condition = ArticleFilterCondition(tag, author, favorited)
        ).map { it.toArticleHttpResponse() }

        return MultipleArticleNestedHttpWrapper(articles, articles.count())
    }

    @GetMapping(GET_ARTICLE_FEED_ENDPOINT)
    suspend fun getFeed(
        @Login token: String,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0
    ): MultipleArticleNestedHttpWrapper<ArticleHttpResponse> {
        val feed = articleService.getFeed(token, ArticlePaging(limit, offset))
            .map { it.toArticleHttpResponse() }

        return MultipleArticleNestedHttpWrapper(feed, feed.count())
    }

    @PutMapping(UPDATE_ARTICLE_ENDPOINT)
    suspend fun update(
        @Login token: String,
        @PathVariable slug: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleUpdateHttpRequest>
    ): SingleArticleNestedHttpWrapper<ArticleHttpResponse> {
        val (title, description, body, tagList) = request.article
        return SingleArticleNestedHttpWrapper(articleService.update(token, ArticleUpdateContent(slug, title, description, body, tagList)).toArticleHttpResponse())
    }

    @DeleteMapping(DELETE_ARTICLE_ENDPOINT)
    suspend fun delete(
        @Login token: String,
        @PathVariable slug: String
    ) = articleService.delete(slug)
}
