package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("COMMENTS")
class Comment (

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("content")
    var content: String,

    @Column("article_id")
    var articleId: Long,

    @Column("is_deleted")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: LocalDateTime? = null
)