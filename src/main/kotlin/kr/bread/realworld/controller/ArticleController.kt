package kr.bread.realworld.controller

import kr.bread.realworld.controller.EndpointConstants.CREATE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.DELETE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.FAVORITE_CANCEL_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.FAVORITE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_ARTICLE_FEED_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_LIST_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_ONE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_TAGS_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.UNFOLLOW_USER_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.UPDATE_ARTICLE_ENDPOINT
import kr.bread.realworld.controller.request.ArticleCreateHttpRequest
import kr.bread.realworld.controller.request.ArticleUpdateHttpRequest
import kr.bread.realworld.domain.article.ArticleService
import kr.bread.realworld.domain.article.SingleArticle
import kr.bread.realworld.support.annotation.AuthToken
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping(GET_ARTICLE_FEED_ENDPOINT)
    suspend fun getFeed(
        @AuthToken token: String,
        @RequestParam(required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(required = false, defaultValue = "0") offset: Int = 0,
    ): MultipleArticleNestedHttpWrapper<SingleArticle> {
        val feed = articleService.getFeed(token, limit, offset)

        return MultipleArticleNestedHttpWrapper(
            feed, feed.count()
        )
    }

    @GetMapping(GET_ONE_ARTICLE_ENDPOINT)
    suspend fun getOne(@PathVariable slug: String) = SingleArticleNestedHttpWrapper(articleService.getOne(slug))

    @PostMapping(CREATE_ARTICLE_ENDPOINT)
    suspend fun create(
        @AuthToken token: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleCreateHttpRequest>,
    ) = SingleArticleNestedHttpWrapper(articleService.create(token, request.article.toArticleContent()))

    @PutMapping(UPDATE_ARTICLE_ENDPOINT)
    suspend fun update(
        @AuthToken token: String,
        @PathVariable slug: String,
        @RequestBody request: SingleArticleNestedHttpWrapper<ArticleUpdateHttpRequest>
    ) {
        val (title, description, body) = request.article
        SingleArticleNestedHttpWrapper(articleService.update(slug, title, description, body))
    }

    @DeleteMapping(UPDATE_ARTICLE_ENDPOINT)
    suspend fun delete(
        @AuthToken token: String,
        @PathVariable slug: String,
    ): SingleArticleNestedHttpWrapper<SingleArticle> {
        return SingleArticleNestedHttpWrapper(articleService.delete(slug))
    }

    @GetMapping(GET_TAGS_ENDPOINT)
    suspend fun getTags() = TagNestedHttpWrapper(articleService.findTags())

    @PostMapping(FAVORITE_ENDPOINT)
    suspend fun favorite(@AuthToken token: String, @PathVariable slug: String): SingleArticleNestedHttpWrapper<SingleArticle> {
        return SingleArticleNestedHttpWrapper(
            articleService.favorite(token, slug)
        )
    }

    @DeleteMapping(FAVORITE_CANCEL_ENDPOINT)
    suspend fun favoriteCancel(@AuthToken token: String, @PathVariable slug: String): SingleArticleNestedHttpWrapper<SingleArticle> {
        return SingleArticleNestedHttpWrapper(
            articleService.unFavorite(token, slug)
        )
    }

}