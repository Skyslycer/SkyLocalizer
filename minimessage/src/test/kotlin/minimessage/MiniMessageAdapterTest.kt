package minimessage

import de.skyslycer.skylocalizer.MiniMessageAdapter

fun main() {
    val stringToParse = "<green>It works!"

    println(MiniMessageAdapter().convert(stringToParse).toString())
}