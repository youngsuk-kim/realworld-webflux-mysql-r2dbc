package kr.bread.realworld.controller

/**
 * created for wrapping json request & response
 *
 * Example
 *
 * {
 *   "article": {
 *     "slug": "how-to-train-your-dragon",
 *     "title": "How to train your dragon",
 *     "description": "Ever wonder how?",
 *     "body": "It takes a Jacobian",
 *     "tagList": ["dragons", "training"],
 *     "createdAt": "2016-02-18T03:22:56.637Z",
 *     "updatedAt": "2016-02-18T03:48:35.824Z",
 *     "favorited": false,
 *     "favoritesCount": 0,
 *     "author": {
 *       "username": "jake",
 *       "bio": "I work at statefarm",
 *       "image": "https://i.stack.imgur.com/xHWG8.jpg",
 *       "following": false
 *     }
 *   }
 * }
 *
 */
data class SingleArticleNestedHttpWrapper<T>(
    val article: T
)


/**
 * created for wrapping json request & response
 *
 * Example
 *
 * {
 *   "articles":[{
 *     "slug": "how-to-train-your-dragon",
 *     "title": "How to train your dragon",
 *     "description": "Ever wonder how?",
 *     "body": "It takes a Jacobian",
 *     "tagList": ["dragons", "training"],
 *     "createdAt": "2016-02-18T03:22:56.637Z",
 *     "updatedAt": "2016-02-18T03:48:35.824Z",
 *     "favorited": false,
 *     "favoritesCount": 0,
 *     "author": {
 *       "username": "jake",
 *       "bio": "I work at statefarm",
 *       "image": "https://i.stack.imgur.com/xHWG8.jpg",
 *       "following": false
 *     }
 *   }, {
 *     "slug": "how-to-train-your-dragon-2",
 *     "title": "How to train your dragon 2",
 *     "description": "So toothless",
 *     "body": "It a dragon",
 *     "tagList": ["dragons", "training"],
 *     "createdAt": "2016-02-18T03:22:56.637Z",
 *     "updatedAt": "2016-02-18T03:48:35.824Z",
 *     "favorited": false,
 *     "favoritesCount": 0,
 *     "author": {
 *       "username": "jake",
 *       "bio": "I work at statefarm",
 *       "image": "https://i.stack.imgur.com/xHWG8.jpg",
 *       "following": false
 *     }
 *   }],
 *   "articlesCount": 2
 * }
 *
 */
data class MultipleArticleNestedHttpWrapper<T>(
    val articles: List<T>,
    val articlesCount: Int
)