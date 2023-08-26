package kr.bread.realworld.domain

fun interface UserFindService {
    suspend fun findById(userId: Long): UserResult
}