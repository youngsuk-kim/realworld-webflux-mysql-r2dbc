package kr.bread.realworld.domain.article

import kr.bread.realworld.infra.ArticleRepository
import org.springframework.stereotype.Component

@Component
class ArticleRemover(
    private val articleRepository: ArticleRepository
) {

    suspend fun remove(articleId: Long) {
        articleRepository.deleteById(articleId)
    }
}