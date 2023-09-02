package kr.bread.realworld.support.utils

import at.favre.lib.crypto.bcrypt.BCrypt

object BCryptUtils {

    fun hash(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())

    fun verify(password: String, hashedPassword: String) =
        BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
}
