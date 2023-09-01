package kr.bread.realworld.controller

import kr.bread.realworld.controller.EndpointConstants.CREATE_COMMENT_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.DELETE_COMMENT_ENDPOINT
import kr.bread.realworld.controller.EndpointConstants.GET_COMMENTS_ENDPOINT
import kr.bread.realworld.controller.request.CommentCreateHttpRequest
import kr.bread.realworld.domain.comment.CommentService
import kr.bread.realworld.domain.comment.SingleComment
import kr.bread.realworld.support.annotation.AuthToken
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping(CREATE_COMMENT_ENDPOINT)
    suspend fun create(
        @AuthToken token: String,
        @PathVariable slug: String,
        @RequestBody request: CommentNestedHttpWrapper<CommentCreateHttpRequest>
    ): CommentNestedHttpWrapper<SingleComment> {
        return CommentNestedHttpWrapper(
            commentService.save(token, request.comment.body, slug)
        )
    }

    @GetMapping(GET_COMMENTS_ENDPOINT)
    suspend fun getComments(
        @AuthToken token: String?,
        @PathVariable slug: String,
    ): CommentsNestedHttpWrapper<List<SingleComment>> {
        return CommentsNestedHttpWrapper(
            commentService.findBySlug(token, slug)
        )
    }

    @DeleteMapping(DELETE_COMMENT_ENDPOINT)
    suspend fun delete(@PathVariable slug: String,
                       @PathVariable id: Long) {
        commentService.delete(slug, id)
    }

}