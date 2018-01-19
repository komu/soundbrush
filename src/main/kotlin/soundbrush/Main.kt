package soundbrush

import java.io.File

private const val TWO_PI = 2 * Math.PI

fun main(args: Array<String>) {
    val freq = 440.0
    val seconds = 0.4

    val buffer = AudioBuffer(seconds, 44100)
    val period = buffer.sampleRate / freq

    for (i in buffer.indices) {
        buffer[i] = Math.sin(TWO_PI * i / period)
    }

    buffer.writeToWaveFile(File("bar.wav"))
}
