package kr.bread.realworld.controller

/**
 * created for wrapping json request & response
 *
 * Example
 *
 * {
 *   "profile": {
 *     "username": "jake",
 *     "bio": "I work at statefarm",
 *     "image": "https://api.realworld.io/images/smiley-cyrus.jpg",
 *     "following": false
 *   }
 * }
 *
 *
 */
data class ProfileNestedHttpWrapper<T>(
    val profile: T
)