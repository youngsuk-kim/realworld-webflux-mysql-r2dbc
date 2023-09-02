package kr.bread.realworld.domain.tag

import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagFinder: TagFinder
) {
    suspend fun findTags(): List<String> {
        return tagFinder.findAll()
            .map { it.name }
    }
}
