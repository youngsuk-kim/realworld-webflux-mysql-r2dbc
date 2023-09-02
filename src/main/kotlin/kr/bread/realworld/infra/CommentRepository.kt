package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.comment.Comment
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface CommentRepository : CoroutineCrudRepository<Comment, Long> {
    fun findByArticleId(articleId: Long): Flow<Comment>
    fun findByIdAndArticleId(id: Long, articleId: Long): Mono<Comment>
}
