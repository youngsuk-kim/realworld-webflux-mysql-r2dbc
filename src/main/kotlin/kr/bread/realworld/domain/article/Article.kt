package kr.bread.realworld.domain.article

import kr.bread.realworld.domain.favorite.Favorite
import kr.bread.realworld.domain.tag.Tag
import kr.bread.realworld.domain.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("article")
class Article(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("slug")
    var slug: String,

    @Column("title")
    var title: String,

    @Column("description")
    var description: String,

    @Column("user_id")
    var userId: Long,

    @Column("body")
    var body: String,

    @Column("is_deleted")
    var isDeleted: Boolean = false,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Transient
    @Value("null")
    var user: User? = null,

    @Transient
    @Value("null")
    var favorites: Set<Favorite>? = null,

    @Transient
    @Value("null")
    var tags: Set<Tag>? = null,
) {

    companion object {
        fun of(articleContent: ArticleContent, userId: Long): Article {
            return Article(
                title = articleContent.title,
                description = articleContent.description,
                body = articleContent.body,
                slug = makeSlug(articleContent.title),
                userId = userId
            )
        }

        private fun makeSlug(title: String): String {
            val lowercase = title.lowercase()

            if (lowercase.contains(" ")) {
                return lowercase.replace(" ", "")
            }

            return lowercase
        }
    }

    fun update(title: String?, description: String?, body: String?) {
        if (!title.isNullOrBlank()) {
            this.title = title
            this.slug = makeSlug(title)
        }

        if (!description.isNullOrBlank()) {
            this.description = description
        }

        if (!body.isNullOrBlank()) {
            this.body = body
        }
    }

    fun delete(): Article {
        this.isDeleted = true

        return this
    }

    fun getFavoriteCount() = this.favorites?.count()

    fun makeRelation(favorites: Set<Favorite>?, tags: Set<Tag>?): Article {
        this.favorites = favorites
        this.tags = tags

        return this
    }

    fun setUser(user: User): Article {
        this.user = user

        return this
    }
}
