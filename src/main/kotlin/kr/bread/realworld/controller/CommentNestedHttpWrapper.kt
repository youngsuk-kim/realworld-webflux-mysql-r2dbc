package kr.bread.realworld.controller

data class CommentNestedHttpWrapper<T>(
    val comment: T
)

data class CommentsNestedHttpWrapper<T>(
    val comments: T
)
