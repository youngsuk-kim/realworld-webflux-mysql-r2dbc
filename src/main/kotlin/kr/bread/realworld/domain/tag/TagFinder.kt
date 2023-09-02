package kr.bread.realworld.domain.tag

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Component

@Component
class TagFinder(
    private val tagRepository: TagRepository
) {
    suspend fun findAll(articleId: Long) = tagRepository.findByArticleId(articleId)
        .buffer().toSet()

    suspend fun findAll() = tagRepository.findAll().buffer().toSet()
}
