package kr.bread.realworld.domain.tag

import org.springframework.stereotype.Component

@Component
class TagUpdater(
    private val tagAppender: TagAppender,
    private val tagRemover: TagRemover
) {

    suspend fun update(articleId: Long, tagList: Set<String>): Set<Tag> {
        tagRemover.removeAll(articleId)

        return tagAppender.appendAll(tagList, articleId)
    }
}