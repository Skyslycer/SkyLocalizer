package de.skyslycer.skylocalizer

import de.skyslycer.skylocalizer.adapter.LocaleAdapter
import net.md_5.bungee.api.ChatColor

class ChatColorAdapter : LocaleAdapter {
    override fun convert(string: String): Any {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
}