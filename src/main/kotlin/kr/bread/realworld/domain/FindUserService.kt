package kr.bread.realworld.domain

import reactor.core.publisher.Mono

fun interface FindUserService {
    fun findById(userId: Long): Mono<User>
}