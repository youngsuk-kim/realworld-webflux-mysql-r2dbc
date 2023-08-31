package kr.bread.realworld.controller

object EndpointConstants {
    const val REGISTER_ENDPOINT = "/api/users"
    const val LOGIN_ENDPOINT = "/api/users/login"
    const val CURRENT_USER_ENDPOINT = "/api/user"
    const val UPDATE_USER_ENDPOINT = "/api/user"
    const val FOLLOW_USER_ENDPOINT = "/api/profiles/{username}/follow"
    const val UNFOLLOW_USER_ENDPOINT = "/api/profiles/{username}/follow"
    const val GET_ONE_ARTICLE_ENDPOINT = "/api/articles/{slug}"
    const val GET_LIST_ARTICLE_ENDPOINT = "/api/articles"
    const val CREATE_ARTICLE_ENDPOINT = "/api/articles"
    const val GET_ARTICLE_FEED_ENDPOINT = "/api/articles/feed"
    const val UPDATE_ARTICLE_ENDPOINT = "/api/articles/{slug}"
    const val DELETE_ARTICLE_ENDPOINT = "/api/articles/{slug}"
    const val GET_TAGS_ENDPOINT = "/api/tags"
}