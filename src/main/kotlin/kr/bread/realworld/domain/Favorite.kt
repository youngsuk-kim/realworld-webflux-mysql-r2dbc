package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "FAVORITE")
class Favorite (

    @Id
    @Column("ID")
    var id: Long? = null,

    @Column("USER_ID")
    var userId: Long,

    @Column("ARTICLE_ID")
    var articleId: Long,

    @Column("IS_DELETED")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime = LocalDateTime.now(),
)