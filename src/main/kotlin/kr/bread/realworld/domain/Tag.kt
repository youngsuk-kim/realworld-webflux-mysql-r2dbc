package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TAG")
class Tag(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("name")
    var name: String,

    @Column("article_id")
    var articleId: Long,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("is_deleted")
    var isDeleted: Boolean = false,
)