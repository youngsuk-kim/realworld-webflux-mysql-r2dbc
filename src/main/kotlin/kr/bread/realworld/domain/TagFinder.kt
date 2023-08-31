package kr.bread.realworld.domain

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.TagRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class TagFinder(
    private val tagRepository: TagRepository
) {

    suspend fun getArticleIdsBy(tagName: String): List<Long> {
        return tagRepository.findByName(tagName).buffer()
            .map { it.articleId }.toList()
    }

    suspend fun findByArticleId(id: Long): Set<String> {
        return tagRepository.findByArticleId(id)
            .buffer().map { it.name }.toSet()
    }

    suspend fun findTagsByArticleId(id: Long): Set<Tag> {
        return tagRepository.findByArticleId(id)
            .buffer().toSet()
    }

}