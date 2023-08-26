package kr.bread.realworld.controller

/**
 * created for wrapping json request & response
 *
 * Example
 *
 * {
 *   "user":{
 *     "email": "jake@jake.jake",
 *     "bio": "I like to skateboard",
 *     "image": "https://i.stack.imgur.com/xHWG8.jpg"
 *   }
 * }
 *
 */
data class UserNestedHttpWrapper<T>(
    val user: T
)
