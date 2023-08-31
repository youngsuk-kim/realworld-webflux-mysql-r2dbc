package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.article.Article
import kr.bread.realworld.domain.article.SingleArticle
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor
import reactor.core.publisher.Mono

interface ArticleRepository: CoroutineCrudRepository<Article, Long>, ReactiveQueryByExampleExecutor<Article> {
    fun findBySlug(slug: String): Mono<Article>
    fun findByIdIn(ids: List<Long>): Flow<Article>

    @Query(
        "SELECT * FROM ARTICLE A " +
                "JOIN TAG T ON A.ID = T.ARTICLE_ID " +
                "LEFT JOIN FAVORITE F ON A.ID = F.ARTICLE_ID " +
                "JOIN USERS U ON A.USER_ID = U.ID " +
                "ORDER BY A.CREATED_AT DESC " +
                "LIMIT :limit OFFSET :offset"
    )
    fun findArticlesByPaging(limit: Int, offset: Int): Flow<SingleArticle>
    fun findByUserId(userId: Long): Mono<Article>
}

