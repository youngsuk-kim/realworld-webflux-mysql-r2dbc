package kr.bread.realworld.domain.comment

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("comments")
class Comment(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("body")
    var body: String,

    @Column("article_id")
    var articleId: Long,

    @Column("user_id")
    var userId: Long,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)