package kr.bread.realworld.infra

import kr.bread.realworld.domain.Follow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FollowRepository: CoroutineCrudRepository<Follow, Long> {
}