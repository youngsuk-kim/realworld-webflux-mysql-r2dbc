package kr.bread.realworld.domain

interface UserFindService {
    suspend fun findById(userId: Long?): UserResult
    suspend fun findByToken(token: String): UserResult
    suspend fun findByUsername(username: String): UserResult
    suspend fun findUserById(userId: Long): User?
}