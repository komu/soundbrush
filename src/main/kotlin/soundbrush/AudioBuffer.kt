package soundbrush

import java.io.File
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import kotlin.math.absoluteValue

class AudioBuffer(seconds: Double, private val sampleRate: Int) {

    private val buffer = DoubleArray((seconds * sampleRate).toInt())

    val size: Int
        get() = buffer.size

    val indices: IntRange
        get() = buffer.indices

    operator fun set(i: Int, value: Double) {
        buffer[i] = value
    }

    fun normalize() {
        val max = buffer.maxBy { it.absoluteValue }!!.absoluteValue
        for (i in buffer.indices)
            buffer[i] /= max
    }

    fun writeToWaveFile(file: File) {
        val bytes = ByteArray(2 * buffer.size)

        buffer.forEachIndexed { i, value ->
            val x = (value * 32767.0).toInt()
            bytes[i * 2 + 0] = x.toByte()
            bytes[i * 2 + 1] = (x ushr 8).toByte()
        }

        val format = AudioFormat(sampleRate.toFloat(), 16, 1, true, false)
        val ais = AudioInputStream(bytes.inputStream(), format, (bytes.size / format.frameSize).toLong())

        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file)
    }
}
