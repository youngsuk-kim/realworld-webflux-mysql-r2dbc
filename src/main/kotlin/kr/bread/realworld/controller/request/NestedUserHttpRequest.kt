package kr.bread.realworld.controller.request

data class NestedUserHttpRequest<T>(
    val user: T
)
