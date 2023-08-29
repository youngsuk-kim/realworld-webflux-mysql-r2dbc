package kr.bread.realworld.domain

import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.infra.TagRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val tagRepository: TagRepository,
) {

    suspend fun create(
        title: String,
        description: String,
        body: String,
        tagList: List<String>?,
    ) {

        val article = articleRepository.save(
            Article(
                title = title,
                description = description,
                body = body
            )
        )

        if (!tagList.isNullOrEmpty()) {
            tagRepository.saveAll(tagList.map { Tag(name = it, articleId = article.id!!) })
        }
    }


}