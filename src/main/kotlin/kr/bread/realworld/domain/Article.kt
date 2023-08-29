package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("ARTICLE")
class Article (

    @Id
    @Column("ID")
    var id: Long? = null,

    @Column("TITLE")
    var title: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("BODY")
    var body: String,

    @Column("IS_DELETED")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("UPDATED_AT")
    var updatedAt: LocalDateTime? = null
)