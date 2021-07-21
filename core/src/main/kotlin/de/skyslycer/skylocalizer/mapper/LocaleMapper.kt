package de.skyslycer.skylocalizer.mapper

import de.skyslycer.skylocalizer.SkyLocalizer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LocaleMapper : ConcurrentHashMap<Locale, SkyLocalizer>() {
    fun addLocale(localizer: SkyLocalizer) {
        this[localizer.locale] = localizer
    }

    @SafeVarargs
    fun addLocales(vararg localizers: SkyLocalizer) {
        localizers.forEach {
            this[it.locale] = it
        }
    }
}