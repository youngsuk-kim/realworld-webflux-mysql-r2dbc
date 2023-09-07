package kr.bread.realworld.domain.favorite

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "favorite")
class Favorite(

    @Id
    @Column("id")
    var id: Long? = null,

    @Column("user_id")
    var userId: Long,

    @Column("article_id")
    var articleId: Long,

    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
