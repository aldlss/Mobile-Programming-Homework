package cn.suwako.speedrun.utils

fun getMd5Digest(input: String): String {
    val md = java.security.MessageDigest.getInstance("MD5")
    return md.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
}