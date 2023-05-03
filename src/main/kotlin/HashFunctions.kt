package okio

import java.security.MessageDigest

data class ByteStringReplacement(val data: ByteArray)

internal inline fun Buffer.digestOverCursor(digest: String): ByteStringReplacement {
    val md = MessageDigest.getInstance(digest)
    readUnsafe().use { cursor ->
        while (cursor.next() != -1) {
            md.update(cursor.data!!, cursor.start, cursor.end - cursor.start)
        }
    }
    return ByteStringReplacement(md.digest()!!)
}

fun Buffer.md5cursor() = digestOverCursor("MD5")
fun Buffer.sha1cursor() = digestOverCursor("SHA-1")
fun Buffer.sha256cursor() = digestOverCursor("SHA-256")
fun Buffer.sha512cursor() = digestOverCursor("SHA-512")