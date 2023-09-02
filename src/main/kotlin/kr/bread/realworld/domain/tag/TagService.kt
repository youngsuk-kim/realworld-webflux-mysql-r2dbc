package kr.bread.realworld.domain.tag

import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagFinder: TagFinder
) {
    suspend fun getAll() = tagFinder.findAll().map { it.name }
}
