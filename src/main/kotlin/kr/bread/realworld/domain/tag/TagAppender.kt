package kr.bread.realworld.domain.tag

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toSet
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Component

@Component
class TagAppender(
    private val tagRepository: TagRepository
) {
    suspend fun appendAll(tagNames: Set<String>?, articleId: Long): Set<Tag> {
        if (tagNames.isNullOrEmpty()) return emptySet()

        return tagRepository.saveAll(tagNames.map { Tag(name = it, articleId = articleId) })
            .buffer().toSet()
    }
}
