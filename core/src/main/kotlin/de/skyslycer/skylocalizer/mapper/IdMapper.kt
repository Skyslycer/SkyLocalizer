package de.skyslycer.skylocalizer.mapper

import de.skyslycer.skylocalizer.Replacement
import de.skyslycer.skylocalizer.SkyLocalizer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class IdMapper(private var defaultLocale: Locale, private val localeMapper: LocaleMapper) : ConcurrentHashMap<Any, Locale>() {
    fun setDefault(locale: Locale): IdMapper {
        this.defaultLocale = locale
        return this
    }

    fun getDefault(): Locale {
        return defaultLocale
    }

    fun getDefaultLocalizer(): SkyLocalizer? {
        return localeMapper[defaultLocale]
    }

    fun addDefault(id: Any) {
        this[id] = defaultLocale
    }

    @SafeVarargs
    fun addDefaults(vararg ids: Any) {
        ids.forEach {
            this[it] = defaultLocale
        }
    }

    fun getLocalizer(id: Any): SkyLocalizer? {
        if (this[id] == null) return null
        if (localeMapper[this[id]] == null) return null
        return localeMapper[this[id]]
    }

    fun get(id: Any, key: String, vararg replacements: Replacement): Any? {
        if (getLocalizer(id) == null) return null
        return getLocalizer(id)!!.get(key, *replacements)
    }

    fun get(id: Any, key: String): Any? {
        if (getLocalizer(id) == null) return null
        return getLocalizer(id)!!.getRaw(key)
    }
}