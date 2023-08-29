package kr.bread.realworld.infra

import kr.bread.realworld.domain.Article
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ArticleRepository: CoroutineCrudRepository<Article, Long> {
}