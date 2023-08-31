package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TagRepository: CoroutineCrudRepository<Tag, Long> {
    fun findByArticleId(articleId: Long): Flow<Tag>
    fun findByName(name: String): Flow<Tag>
    fun findAllByArticleId(articleId: Long, pageable: Pageable): Flow<Tag>
}