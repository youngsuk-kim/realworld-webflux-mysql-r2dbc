package kr.bread.realworld.domain.comment

import kr.bread.realworld.infra.CommentRepository
import org.springframework.stereotype.Component

@Component
class CommentRemover(
    private val commentRepository: CommentRepository
) {

    suspend fun remove(comment: Comment) {
        commentRepository.delete(comment)
    }
}