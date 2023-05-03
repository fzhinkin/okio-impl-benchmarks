package okio

import kotlinx.benchmark.*

@State(Scope.Benchmark)
open class MessageDigestBenchmarks {
    private var buffer: Buffer = Buffer()

    @Param("1", "13", "32", "128", "1024", "8196", "10000")
    var size: Int = 0

    @Setup
    fun setup() {
        buffer = Buffer()
        buffer.write(ByteArray(size) { it.toByte() })
    }

    @Benchmark
    fun md5baseline() = buffer.md5()

    @Benchmark
    fun md5cursor() = buffer.md5cursor()

    @Benchmark
    fun sha1baseline() = buffer.sha1()

    @Benchmark
    fun sha1cursor() = buffer.sha1cursor()

    @Benchmark
    fun sha256baseline() = buffer.sha256()

    @Benchmark
    fun sha256cursor() = buffer.sha256cursor()

    @Benchmark
    fun sha512baseline() = buffer.sha512()

    @Benchmark
    fun sha512cursor() = buffer.sha512cursor()
}