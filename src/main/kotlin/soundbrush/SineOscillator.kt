package soundbrush

import kotlin.math.PI
import kotlin.math.sin

class SineOscillator(frequency: Double, sampleRate: Int) {

    private val phaseIncrement = frequency * PI * 2 / sampleRate

    fun sample(time: Int): Double = sin(time * phaseIncrement)
}
