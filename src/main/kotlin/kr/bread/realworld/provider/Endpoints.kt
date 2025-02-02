package kr.bread.realworld.provider

object Endpoints {
    /**
     * Auth
     */
    const val REGISTER_ENDPOINT = "/api/users"
    const val LOGIN_ENDPOINT = "/api/users/login"

    /**
     * User
     */
    const val CURRENT_USER_ENDPOINT = "/api/user"
    const val UPDATE_USER_ENDPOINT = "/api/user"

    /**
     * Follow
     */
    const val FOLLOW_USER_ENDPOINT = "/api/profiles/{username}/follow"
    const val UNFOLLOW_USER_ENDPOINT = "/api/profiles/{username}/follow"

    /**
     * Article
     */
    const val GET_ONE_ARTICLE_ENDPOINT = "/api/articles/{slug}"
    const val GET_LIST_ARTICLE_ENDPOINT = "/api/articles"
    const val CREATE_ARTICLE_ENDPOINT = "/api/articles"
    const val GET_ARTICLE_FEED_ENDPOINT = "/api/articles/feed"
    const val UPDATE_ARTICLE_ENDPOINT = "/api/articles/{slug}"
    const val DELETE_ARTICLE_ENDPOINT = "/api/articles/{slug}"

    /**
     * Tag
     */
    const val GET_TAGS_ENDPOINT = "/api/tags"

    /**
     * Favorite
     */
    const val FAVORITE_ENDPOINT = "/api/articles/{slug}/favorite"
    const val FAVORITE_CANCEL_ENDPOINT = "/api/articles/{slug}/favorite"

    /**
     * Comment
     */
    const val CREATE_COMMENT_ENDPOINT = "/api/articles/{slug}/comments"
    const val GET_COMMENTS_ENDPOINT = "/api/articles/{slug}/comments"
    const val DELETE_COMMENT_ENDPOINT = "/api/articles/{slug}/comments/{id}"
}
