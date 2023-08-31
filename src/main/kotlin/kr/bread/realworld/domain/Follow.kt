package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "FOLLOW")
class Follow(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("follower_id")
    var followerId: Long,

    @Column("followee_id")
    var followeeId: Long,

    @Column("unfollow")
    var unfollow: Boolean = false,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
) {
    fun unfollow() {
        this.unfollow = true
    }
}