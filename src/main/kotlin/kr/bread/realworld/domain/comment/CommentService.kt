package kr.bread.realworld.domain.comment

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.bread.realworld.domain.article.ArticleFinder
import kr.bread.realworld.domain.follow.FollowService
import kr.bread.realworld.infra.CommentRepository
import kr.bread.realworld.support.exception.CommentNotFoundException
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val userFindServiceIn: UserFindServiceIn,
    private val articleFinder: ArticleFinder,
    private val commentRepository: CommentRepository,
    private val followService: FollowService
) {

    suspend fun save(token: String, body: String, slug: String): CommentResult {
        val commentResult = coroutineScope {
            val deferredUser = async {
                userFindServiceIn.findByToken(token)
            }.await()

            val deferredArticle = async {
                articleFinder.findBySlug(slug)
            }.await()
            val comment =
                Comment(body = body, articleId = deferredArticle.id!!, userId = deferredUser.id)

            commentRepository.save(comment)

            val profileResult = followService.findFollow(token, deferredUser.username)

            CommentResult.of(comment, profileResult)
        }

        return commentResult
    }

    suspend fun findBySlug(token: String?, slug: String): List<CommentResult> {
        val article = articleFinder.findBySlug(slug)
        val comments = commentRepository.findByArticleId(article.id!!)

        return comments.map { comment ->
            val user = userFindServiceIn.findById(article.userId)
            val userResult = token?.let { followService.findFollow(token, user.username) }

            CommentResult.of(comment, userResult)
        }.buffer().toList()
    }

    suspend fun delete(slug: String, commentId: Long) {
        val article = articleFinder.findBySlug(slug)
        val comment = commentRepository.findByIdAndArticleId(commentId, article.id!!)
        comment.awaitSingleOrNull()?.delete() ?: throw CommentNotFoundException()

        commentRepository.save(comment.awaitSingle())
    }
}
