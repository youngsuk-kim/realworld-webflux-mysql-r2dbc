package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.comment.CommentResult
import kr.bread.realworld.domain.comment.CommentService
import kr.bread.realworld.provider.ApiEndpoints.CREATE_COMMENT_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.DELETE_COMMENT_ENDPOINT
import kr.bread.realworld.provider.ApiEndpoints.GET_COMMENTS_ENDPOINT
import kr.bread.realworld.provider.CommentNestedHttpWrapper
import kr.bread.realworld.provider.CommentsNestedHttpWrapper
import kr.bread.realworld.provider.request.CommentCreateHttpRequest
import kr.bread.realworld.support.annotation.Login
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentApi(
    private val commentService: CommentService
) {

    @PostMapping(CREATE_COMMENT_ENDPOINT)
    suspend fun create(
        @Login token: String,
        @PathVariable slug: String,
        @RequestBody request: CommentNestedHttpWrapper<CommentCreateHttpRequest>
    ): CommentNestedHttpWrapper<CommentResult> {
        return CommentNestedHttpWrapper(
            commentService.save(token, request.comment.body, slug)
        )
    }

    @GetMapping(GET_COMMENTS_ENDPOINT)
    suspend fun getComments(
        @Login token: String?,
        @PathVariable slug: String
    ): CommentsNestedHttpWrapper<List<CommentResult>> {
        return CommentsNestedHttpWrapper(
            commentService.findBySlug(token, slug)
        )
    }

    @DeleteMapping(DELETE_COMMENT_ENDPOINT)
    suspend fun delete(
        @PathVariable slug: String,
        @PathVariable id: Long
    ) {
        commentService.delete(slug, id)
    }
}
