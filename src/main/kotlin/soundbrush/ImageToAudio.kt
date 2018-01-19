package soundbrush

import java.awt.image.Raster

fun Raster.toSound(durationSeconds: Double = 3.0, yFactor: Int = 2, maxSpecFreq: Int = 20000): AudioBuffer {
    val sampleRate = 44100
    val buffer = AudioBuffer(durationSeconds, sampleRate)
    val samplesPerPixel = buffer.size / width
    val c = maxSpecFreq / height
    val rgb = IntArray(3)

    for (i in buffer.indices) {
        val x = (i / samplesPerPixel).coerceAtMost(width - 1)

        var sample = 0.0

        for (y in 0 until height step yFactor) {
            getPixel(x, y, rgb)

            val sum = rgb[0] + rgb[1] + rgb[2]
            val gain = Math.pow(sum * 100 / 765.0, 2.0)
            val frequency = (c * (height - y + 1))

            sample += gain * Math.sin(frequency * Math.PI * 2 * i / sampleRate)
        }

        buffer[i] = sample
    }

    buffer.normalize()
    return buffer
}
