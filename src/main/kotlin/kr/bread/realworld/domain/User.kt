package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "USERS")
class User(

    @Id
    @Column("ID")
    val id: Long? = null,

    @CreatedDate
    @Column("CREATED_AT")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("UPDATED_AT")
    val updatedAt: LocalDateTime? = null,
)