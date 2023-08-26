package kr.bread.realworld.support.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistsException(
    override val message: String = "user already exists.",
) : ServerException(409, message)

data class PasswordNotMatchedException(
    override val message: String = "wrong password.",
) : ServerException(400, message)

data class InvalidJwtTokenException(
    override val message: String = "invalid token.",
) : ServerException(400, message)

data class UnauthorizedException(
    override val message: String = "requires authentication but it isn't provided.",
) : ServerException(401, message)

data class UserForbiddenException(
    override val message: String = "user doesn't have permissions to perform the action.",
) : ServerException(401, message)

data class UserNotFoundException(
    override val message: String = "user not found.",
) : ServerException(404, message)

data class EmailAlreadyRegisterException(
    override val message: String = "email is already register.",
) : ServerException(400, message)

