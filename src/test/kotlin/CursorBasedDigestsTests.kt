import kotlin.test.Test

import okio.*
import kotlin.test.assertContentEquals

class CursorBasedDigestsTests {
    @Test
    fun testMd5() {
        for (size in arrayOf(0, 1, 8, 12, 128, 1024, 2048, 10000, 20000)) {
            val buffer = buffer(size)
            assertContentEquals(buffer.md5().toByteArray(), buffer.md5cursor().data)
        }
    }

    private fun buffer(size: Int): Buffer = Buffer().write(ByteArray(size) {it.toByte()})
}