package kr.bread.realworld.domain

fun interface FindUserService {
    suspend fun findById(userId: Long): UserResult
}