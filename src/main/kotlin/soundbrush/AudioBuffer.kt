package soundbrush

import java.io.File
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

class AudioBuffer(seconds: Double, val sampleRate: Int) {

    private val buffer = DoubleArray((seconds * sampleRate).toInt())

    val indices: IntRange
        get() = buffer.indices

    operator fun set(i: Int, value: Double) {
        buffer[i] = value
    }

    fun writeToWaveFile(file: File) {
        val byteBuffer = ByteArray(buffer.size * 2)

        for (i in buffer.indices) {
            val x = (buffer[i] * 32767.0).toInt()
            byteBuffer[i * 2] = x.toByte()
            byteBuffer[i * 2 + 1] = x.ushr(8).toByte()
        }

        val format = AudioFormat(sampleRate.toFloat(), 16, 1, true, false)
        val ais = AudioInputStream(byteBuffer.inputStream(), format, (byteBuffer.size / format.frameSize).toLong())

        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file)
    }
}
