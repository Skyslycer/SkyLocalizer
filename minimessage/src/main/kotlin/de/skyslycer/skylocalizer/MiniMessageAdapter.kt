package de.skyslycer.skylocalizer

import de.skyslycer.skylocalizer.adapter.LocaleAdapter
import net.kyori.adventure.text.minimessage.MiniMessage

class MiniMessageAdapter : LocaleAdapter {
    override fun convert(string: String): Any {
        return MiniMessage.get().parse(string)
    }
}