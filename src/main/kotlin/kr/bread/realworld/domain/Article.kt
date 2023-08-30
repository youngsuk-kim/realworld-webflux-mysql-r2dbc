package kr.bread.realworld.domain

import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("ARTICLE")
class Article(

    @Id
    @Column("ID")
    var id: Long? = null,

    @Column("SLUG")
    var slug: String,

    @Column("TITLE")
    var title: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("USER_ID")
    var userId: Long,

    @Column("BODY")
    var body: String,

    @Column("IS_DELETED")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("UPDATED_AT")
    var updatedAt: LocalDateTime? = null,
) {

    companion object {
        fun of(title: String, description: String, body: String, userId: Long): Article {
            return Article(title = title, description = description, body = body, slug = makeSlug(title), userId = userId)
        }

        private fun makeSlug(title: String): String {
            val lowercase = title.lowercase()

            if (lowercase.contains(" ")) {
                return lowercase.replace(" ", "")
            }

            return lowercase
        }
    }
}