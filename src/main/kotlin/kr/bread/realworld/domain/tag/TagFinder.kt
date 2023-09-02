package kr.bread.realworld.domain.tag

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Component

@Component
class TagFinder(
    private val tagRepository: TagRepository
) {

    suspend fun findByArticleId(id: Long): Set<String> {
        return tagRepository.findByArticleId(id)
            .buffer().map { it.name }.toSet()
    }

    suspend fun findTagsByArticleId(id: Long): Set<Tag> {
        return tagRepository.findByArticleId(id)
            .buffer().toSet()
    }

    suspend fun findAll(): Set<Tag> {
        return tagRepository.findAll().buffer().toSet()
    }
}
