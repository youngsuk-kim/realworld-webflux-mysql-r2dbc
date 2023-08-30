package kr.bread.realworld.infra

import kr.bread.realworld.domain.Article
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface ArticleRepository: CoroutineCrudRepository<Article, Long> {
    fun findBySlug(slug: String): Mono<Article>
}