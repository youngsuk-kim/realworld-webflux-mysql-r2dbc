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
    var id: Long? = null,

    @Column("USERNAME")
    var username: String,

    @Column("EMAIL")
    var email: String,

    @Column("PASSWORD")
    var password: String,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("UPDATED_AT")
    var updatedAt: LocalDateTime? = null,
) {
    companion object {

        /**
         * for register user
         */
        fun of(username: String, email: String, password: String): User {
            return User(username = username, email = email, password = password)
        }
    }
}