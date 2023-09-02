package kr.bread.realworld.domain.user

import kr.bread.realworld.domain.article.Article
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "USERS")
class User(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("username")
    var username: String,

    @Column("email")
    var email: String,

    @Column("bio")
    var bio: String? = null,

    @Column("image")
    var image: String? = null,

    @Column("password")
    var password: String,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: LocalDateTime? = null,

    @Transient
    @Value("null")
    var article: Set<Article>? = null
) {
    companion object {

        /**
         * for register user
         */
        fun of(username: String, email: String, password: String) =
            User(username = username, email = email, password = password)
    }

    fun update(email: String?, bio: String?, image: String?): User {
        if (!email.isNullOrBlank()) {
            this.email = email
        }

        if (!bio.isNullOrBlank()) {
            this.bio = bio
        }

        if (!image.isNullOrBlank()) {
            this.image = image
        }

        return this
    }
}
