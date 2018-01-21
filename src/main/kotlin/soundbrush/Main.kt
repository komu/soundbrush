package soundbrush

import java.io.File
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: soundbrush INPUT.jpg")
        System.exit(1)
    }

    val input = File(args[0])
    val output = File(input.name.replace(".jpg", ".wav"))
    val image = ImageIO.read(input)

    val time = measureTimeMillis {
        val buffer = image.raster.toSound()

        buffer.writeToWaveFile(output)
    }

    println("wrote $output in $time ms")
}
