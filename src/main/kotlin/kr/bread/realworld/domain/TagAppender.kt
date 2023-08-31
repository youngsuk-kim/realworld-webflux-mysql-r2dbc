package kr.bread.realworld.domain

import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.toList
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Component

@Component
class TagAppender(
    private val tagRepository: TagRepository
) {
    suspend fun append(tag: Tag): Tag {
        return tagRepository.save(tag)
    }

    suspend fun appendAll(tagNames: List<String>?, articleId: Long): List<Tag> {
        if (tagNames.isNullOrEmpty()) return emptyList()

        return tagRepository.saveAll(tagNames.map { Tag(name = it, articleId = articleId) })
            .buffer().toList()
    }
}