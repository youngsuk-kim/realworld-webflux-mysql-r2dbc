package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.favorite.FavoriteService
import kr.bread.realworld.provider.Endpoints.FAVORITE_CANCEL_ENDPOINT
import kr.bread.realworld.provider.Endpoints.FAVORITE_ENDPOINT
import kr.bread.realworld.provider.SingleArticleNestedHttpWrapper
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FavoriteApi(
    private val favoriteService: FavoriteService
) {

    @PostMapping(FAVORITE_ENDPOINT)
    suspend fun create(@Login token: String, @PathVariable slug: String) =
        SingleArticleNestedHttpWrapper(
            favoriteService.favorite(token, slug).toArticleHttpResponse()
        )

    @DeleteMapping(FAVORITE_CANCEL_ENDPOINT)
    suspend fun cancel(@Login token: String, @PathVariable slug: String) =
        SingleArticleNestedHttpWrapper(
            favoriteService.unFavorite(token, slug).toArticleHttpResponse()
        )
}
