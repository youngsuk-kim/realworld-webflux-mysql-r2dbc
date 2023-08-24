package kr.bread.realworld.support.exception

/**
 *
 * << error response format >>
 *
 * {
 *   "errors":{
 *     "body": [
 *       "can't be empty"
 *     ]
 *   }
 * }
 */

data class ErrorResponse(
    val code: Int,
    val message: String,
)
