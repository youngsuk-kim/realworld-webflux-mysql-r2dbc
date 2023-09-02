package kr.bread.realworld.provider

data class CommentNestedHttpWrapper<T>(
    val comment: T
)

data class CommentsNestedHttpWrapper<T>(
    val comments: T
)

data class ProfileNestedHttpWrapper<T>(
    val profile: T
)
data class TagNestedHttpWrapper<T>(
    val tags: T
)

data class UserNestedHttpWrapper<T>(
    val user: T
)

data class SingleArticleNestedHttpWrapper<T>(
    val article: T
)

data class MultipleArticleNestedHttpWrapper<T>(
    val articles: List<T>,
    val articlesCount: Int
)
