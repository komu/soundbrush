package soundbrush

import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: soundbrush INPUT.jpg")
        System.exit(1)
    }

    val input = File(args[0])
    val output = File(input.name.replace(".jpg", ".wav"))
    val image = ImageIO.read(input)

    val buffer = image.raster.toSound(3.0, yFactor = 8)

    buffer.writeToWaveFile(output)

    println("wrote $output")
}
