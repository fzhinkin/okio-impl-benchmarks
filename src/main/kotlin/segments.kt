package org.example

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import java.nio.ByteBuffer
import java.util.concurrent.ThreadLocalRandom

sealed interface InheritableSegment {
}

class ByteArraySegment(val data: ByteArray) : InheritableSegment {
}

class ByteBufferSegment(val data: ByteBuffer) : InheritableSegment {
}

class PanamaSegment() : InheritableSegment {
}

class SomeOtherSegment() : InheritableSegment {
}

class EmbeddedSegment(val type: Int, val f0: Int, val f1: Int, val f2: Int) {
}

interface DispatchableSegment {
    fun call(): Int
}

@State(Scope.Benchmark)
open class DispatchBenchmarks {
    private var segments = arrayOf(
        ByteBufferSegment(ByteBuffer.allocate(10)),
        SomeOtherSegment(),
        ByteArraySegment(ByteArray(10)),
        PanamaSegment())
    private var embeddedSegments = arrayOf(
        EmbeddedSegment(0, 0, 1, 2),
        EmbeddedSegment(1, 0, 1, 2),
        EmbeddedSegment(2, 0, 1, 2),
        EmbeddedSegment(3, 0, 1, 2))
    private val dispatchableSegments = arrayOf(
        object : DispatchableSegment { override fun call(): Int = 0 },
        object : DispatchableSegment { override fun call(): Int = 1 },
        object : DispatchableSegment { override fun call(): Int = 2 },
        object : DispatchableSegment { override fun call(): Int = 3 }
    )
    private var idx: Int = 0

    @Benchmark
    fun typeSwitch(): Int {
        val i = idx
        idx = (idx + 1) % segments.size
        return when (segments[i]) {
            is ByteArraySegment -> 0
            is ByteBufferSegment -> 1
            is PanamaSegment -> 2
            is SomeOtherSegment -> 3
            else -> -1
        }
    }

    @Benchmark
    fun embeddedValue() : Int {
        val i = idx
        idx = (idx + 1) % embeddedSegments.size
        val embeddedSegment = embeddedSegments[i]
        return when (embeddedSegment.type) {
            0 -> 21
            1 -> 17
            2 -> 11
            3 -> 5
            else -> -1
        }
    }

    @Benchmark
    fun vcall(): Int {
        val i = idx
        idx = (idx + 1) % dispatchableSegments.size
        return dispatchableSegments[i].call()
    }

    @Benchmark
    fun baseline(): Int {
        idx = (idx + 1) % dispatchableSegments.size
        return -1
    }
}