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
    @Column("ID")
    var id: Long? = null,

    @Column("CONTENT")
    var content: String,

    @Column("ARTICLE_ID")
    var articleId: Long,

    @Column("IS_DELETED")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("UPDATED_AT")
    var updatedAt: LocalDateTime? = null
)