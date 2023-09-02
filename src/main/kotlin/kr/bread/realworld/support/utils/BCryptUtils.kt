package kr.bread.realworld.support.utils

import at.favre.lib.crypto.bcrypt.BCrypt

private const val hashCost = 12

object BCryptUtils {

    fun hash(password: String): String =
        BCrypt.withDefaults().hashToString(hashCost, password.toCharArray())

    fun verify(password: String, hashedPassword: String) =
        BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
}
