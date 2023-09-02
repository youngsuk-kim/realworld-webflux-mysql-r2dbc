package kr.bread.realworld.infra

import kotlinx.coroutines.flow.Flow
import kr.bread.realworld.domain.tag.Tag
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TagRepository : CoroutineCrudRepository<Tag, Long> {
    fun findByArticleId(articleId: Long): Flow<Tag>
}
