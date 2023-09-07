package kr.bread.realworld.domain.tag

import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Component

@Component
class TagRemover(
    private val tagRepository: TagRepository
) {

    suspend fun removeAll(articleId: Long) {
        val tags = tagRepository.findByArticleId(articleId)

        tagRepository.deleteAll(tags)
    }
}