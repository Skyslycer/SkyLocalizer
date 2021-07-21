package chatcolor

import de.skyslycer.skylocalizer.ChatColorAdapter

fun main() {
    val chatColorAdapter = ChatColorAdapter()
    val stringToTranslate = "&aBukkit &7ChatColor &bTest"

    println(chatColorAdapter.convert(stringToTranslate))
}