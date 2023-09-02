package kr.bread.realworld.support.exception

sealed class ServerException(
    val code: Int,
    override val message: String
) : RuntimeException(message)

data class PasswordNotMatchedException(
    override val message: String = "wrong password."
) : ServerException(400, message)

data class InvalidJwtTokenException(
    override val message: String = "invalid token."
) : ServerException(400, message)

data class UserNotFoundException(
    override val message: String = "user not found."
) : ServerException(404, message)

data class EmailAlreadyRegisterException(
    override val message: String = "email is already register."
) : ServerException(400, message)

data class ArticleNotFoundException(
    override val message: String = "no article exists."
) : ServerException(404, message)

data class TagNotFoundException(
    override val message: String = "no tag exists."
) : ServerException(404, message)

data class CommentNotFoundException(
    override val message: String = "no comment exists."
) : ServerException(404, message)

data class FollowNotFoundException(
    override val message: String = "no follow exists."
) : ServerException(404, message)
