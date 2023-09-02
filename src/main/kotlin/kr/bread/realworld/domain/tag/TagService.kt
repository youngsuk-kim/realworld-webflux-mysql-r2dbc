package kr.bread.realworld.domain.tag

import kr.bread.realworld.domain.article.Article
import kr.bread.realworld.domain.article.ArticleContent
import kr.bread.realworld.domain.article.ArticleResult
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagFinder: TagFinder
) {
    suspend fun findTags(): List<String> {
        return tagFinder.findAll()
            .map { it.name }
    }
}