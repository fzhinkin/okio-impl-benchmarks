package okio

import kotlinx.benchmark.*

@State(Scope.Benchmark)
open class BufferGetBenchmarks {
    private var buffer = Buffer()

    @Param("1", "32", "128", "1024", "8196")
    var size: Int = 1

    @Setup
    fun setup() {
        buffer = Buffer()
        buffer.write(ByteArray(size))
    }

    @Benchmark
    fun rawAccess(): Int {
        var sum = 0
        for (i in 0 until size.toLong()) {
            sum += buffer[i]
        }
        return sum
    }

    @Benchmark
    fun cursorAccess(): Int {
        var sum = 0;
        buffer.readUnsafe().use { cursor ->
            while (cursor.next() >= 0) {
                for (i in cursor.start until cursor.end) {
                    sum += cursor.data!![i]
                }
            }
        }
        return sum
    }

    @Benchmark
    fun cloneAndConsume(): Int {
        val copy = buffer.copy()
        var sum = 0
        for (i in 0 until size) {
            sum += copy.readByte()
        }
        return sum
    }
}