package kr.bread.realworld.infra

import kr.bread.realworld.domain.Tag
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TagRepository: CoroutineCrudRepository<Tag, Long> {
}