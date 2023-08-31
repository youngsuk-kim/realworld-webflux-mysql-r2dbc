package kr.bread.realworld.domain

import kr.bread.realworld.infra.ArticleRepository
import org.springframework.stereotype.Component

@Component
class ArticleAppender(
    private val articleRepository: ArticleRepository
) {

    suspend fun append(article: Article): Article {
        return articleRepository.save(article)
    }
}