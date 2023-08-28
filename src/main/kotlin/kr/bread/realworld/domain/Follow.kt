package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "FOLLOW")
class Follow(

    @Id
    var id: Long? = null,

    @Column("FOLLOWER_ID")
    var followerId: Long,

    @Column("FOLLOWEE_ID")
    var followeeId: Long,

    @Column("UNFOLLOW")
    var unfollow: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
) {
    fun unfollow() {
        this.unfollow = true
    }
}