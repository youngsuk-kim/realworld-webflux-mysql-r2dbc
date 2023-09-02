package kr.bread.realworld.domain.tag

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tag")
class Tag(

    @Id
    @Column("id")
    val id: Long? = null,

    @Column("name")
    var name: String,

    @Column("article_id")
    var articleId: Long,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun id(): Long {
        requireNotNull(this.id) { "id cannot be null" }
        return this.id
    }
}
