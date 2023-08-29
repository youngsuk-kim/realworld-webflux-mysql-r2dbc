package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TAG")
class Tag (

    @Id
    @Column("ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("ARTICLE_ID")
    var articleId: Long,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime = LocalDateTime.now()
)