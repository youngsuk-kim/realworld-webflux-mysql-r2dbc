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
import kr.bread.realworld.domain.user.UserFinder
import kr.bread.realworld.infra.CommentRepository
import kr.bread.realworld.support.exception.CommentNotFoundException
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val userFinder: UserFinder,
    private val articleFinder: ArticleFinder,
    private val commentRepository: CommentRepository,
    private val followService: FollowService,
    private val commentRemover: CommentRemover
) {

    suspend fun save(token: String, body: String, slug: String): CommentResult {
        val commentResult = coroutineScope {
            val deferredUser = async { userFinder.findByToken(token) }
            val deferredArticle = async { articleFinder.findOne(slug) }

            val comment = Comment(
                body = body,
                articleId = deferredArticle.await().id!!,
                userId = deferredUser.await().id!!
            )

            commentRepository.save(comment)

            val followerResult = followService.findFollow(token, deferredUser.await().id!!)

            CommentResult.of(comment, followerResult)
        }

        return commentResult
    }

    suspend fun getBySlug(token: String?, slug: String): List<CommentResult> {
        val article = articleFinder.findOne(slug)
        val comments = commentRepository.findByArticleId(article.id!!)

        return comments.map { comment ->
            val user = userFinder.findById(article.userId)
            val userResult = token?.let { followService.findFollow(token, user.id!!) }

            CommentResult.of(comment, userResult)
        }.buffer().toList()
    }

    suspend fun delete(slug: String, commentId: Long) {
        val article = articleFinder.findOne(slug)
        val comment = commentRepository.findByIdAndArticleId(commentId, article.id!!)
            .awaitSingleOrNull() ?: throw CommentNotFoundException()

        commentRemover.remove(comment)
    }
}
