package soundbrush

import java.awt.image.Raster
import kotlin.math.log10
import kotlin.math.pow

fun Raster.toSound(durationSeconds: Double = 3.0, minFreq: Int = 0, maxFreq: Int = 20000): AudioBuffer {

    val sampleRate = 44100
    val buffer = AudioBuffer(durationSeconds, sampleRate)
    val samplesPerPixel = buffer.size / width
    val rgb = IntArray(3)

    val maxFreqLog = log10((maxFreq - minFreq).toDouble())
    val step = maxFreqLog / height

    for (i in buffer.indices) {
        val x = (i / samplesPerPixel).coerceAtMost(width - 1)

        var sample = 0.0

        for (y in 0 until height) {
            getPixel(x, y, rgb)

            val sum = rgb[0] + rgb[1] + rgb[2]
            val gain = Math.pow(sum * 100 / 765.0, 2.0)
            val yy = height - y + 1

            val frequency = minFreq + 10.0.pow(step * yy)
            sample += gain * Math.sin(frequency * Math.PI * 2 * i / sampleRate)
        }

        buffer[i] = sample
    }

    buffer.normalize()
    return buffer
}
