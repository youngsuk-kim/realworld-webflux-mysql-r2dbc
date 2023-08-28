package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "FOLLOW")
class Follow(

    @Id
    val id: Long? = null,

    @Column("FOLLOWER_ID")
    val followerId: Long,

    @Column("FOLLOWEE_ID")
    val followeeId: Long,

    @Column("UNFOLLOW")
    var unfollow: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
)