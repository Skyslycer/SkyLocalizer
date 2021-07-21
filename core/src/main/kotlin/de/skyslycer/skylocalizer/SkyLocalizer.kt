package de.skyslycer.skylocalizer

import de.skyslycer.skylocalizer.adapter.LocaleAdapter
import de.skyslycer.skylocalizer.adapter.StringAdapter
import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import de.skyslycer.skylocalizer.reader.LocaleReader
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

/**
 * Create a new localizer
 *
 * @param reader The text for the locale, if it isn't a handled type, it throws an UnhandledReaderTypeException
 * @param localeReader The reader that should be used to read the file. The reader defines what types it can handle
 * @param locale The locale that this localization is for
 * @param localeAdapter The adapter that should be used to parse the messages
 * @param placeholderPrefix The prefix for placeholders 'prefix' 'placeholder' 'suffix'
 * @param placeholderSuffix The suffix for placeholders 'prefix' 'placeholder' 'suffix'
 *
 * @return The newly built localizer
 *
 * @see LocaleAdapter
 * @see LocaleReader
 */
class SkyLocalizer(private val reader: Any, private val localeReader: LocaleReader, val locale: Locale, private val localeAdapter: LocaleAdapter, private val placeholderPrefix: String, private val placeholderSuffix: String) {
    constructor(reader: Any, localeReader: LocaleReader, locale: Locale) : this(reader, localeReader, locale, StringAdapter(), "%", "%")
    constructor(reader: Any, localeReader: LocaleReader, locale: Locale, localeAdapter: LocaleAdapter) : this(reader, localeReader, locale, localeAdapter, "%", "%")
    constructor(reader: Any, localeReader: LocaleReader, locale: Locale, placeholder: String) : this(reader, localeReader, locale, StringAdapter(), placeholder, placeholder)
    constructor(reader: Any, localeReader: LocaleReader, locale: Locale, localeAdapter: LocaleAdapter, placeholder: String) : this(reader, localeReader, locale, localeAdapter, placeholder, placeholder)
    constructor(reader: Any, localeReader: LocaleReader, locale: Locale, placeholderPrefix: String, placeholderSuffix: String) : this(reader, localeReader, locale, StringAdapter(), placeholderPrefix, placeholderSuffix)


    private val localizations = mutableMapOf<String, String>()

    /**
     * Load the strings from the file with the given reader and text
     *
     * @return The localizer with the added strings
     *
     * @throws IOException
     * @throws UnhandledReaderTypeException
     */
    @Throws(IOException::class, UnhandledReaderTypeException::class)
    fun load(): SkyLocalizer {
        localizations.putAll(localeReader.read(reader))
        return this
    }

    /**
     * Load the strings from the file with the newly given reader and text
     *
     * @param reader The reader that should be read from
     * @param localeReader The locale reader that should read the text
     *
     * @return The localizer with the added strings
     *
     * @throws IOException
     * @throws UnhandledReaderTypeException
     */
    @Throws(IOException::class, UnhandledReaderTypeException::class)
    fun loadAdditional(reader: Any, localeReader: LocaleReader): SkyLocalizer {
        localizations.putAll(localeReader.read(reader))
        return this
    }

    /**
     * Add a localization to the localizations
     * WARNING: Overwrites when there is a key
     *
     * @param key The key for the localization
     * @param value The value for the key
     *
     * @return The localizer with the added string
     */
    fun add(key: String, value: String): SkyLocalizer {
        localizations[key] = value
        return this
    }

    /**
     * Add a localizations to the localizations
     * WARNING: Overwrites when there is a key
     *
     * @param map The localizations to add
     *
     * @return The localizer with the added string
     */
    fun addAll(map: Map<String, String>): SkyLocalizer {
        localizations.putAll(map)
        return this
    }

    /**
     * Remove a localization by a key
     *
     * @param key The key that should be deleted
     *
     * @return If something has been removed
     */
    fun remove(key: String): Boolean {
        if (localizations.containsKey(key)) {
            localizations.remove(key)
            return true
        }

        return false
    }

    /**
     * Remove a localization by a key and a value
     * Only removes when they key matches the value
     *
     * @param key The key that should be deleted
     * @param value The value that should match the key
     *
     * @return If something has been removed
     */
    fun remove(key: String, value: String): Boolean {
        if (localizations[key] == value) {
            localizations.remove(key)
            return true
        }

        return false
    }

    /**
     * Remove a localizations by keys
     *
     * @param keys The keys that should be deleted
     *
     * @return If something has been removed
     */
    fun removeAll(keys: List<String>): Boolean {
        var hasDeleted = false

        keys.forEach {
            if (localizations.remove(it) != null) hasDeleted = true
        }

        return hasDeleted
    }

    /**
     * Remove localizations by a key and a value
     * Only removes when they key matches the value
     *
     * @param map The map that contains all the keys with the mapped values
     *
     * @return If something has been removed
     */
    fun removeAll(map: Map<String, String>): Boolean {
        var hasDeleted = false

        map.forEach { (key, value) ->
            if (localizations[key] == value) hasDeleted = true; localizations.remove(key)
        }

        return hasDeleted
    }

    /**
     * Returns the raw message without converting it or replacing placeholders
     *
     * @param key The message key
     *
     * @return Null if there isn't any message by this key, else the raw message
     *
     * @see SkyLocalizer.get
     */
    fun getRaw(key: String): String? {
        if (!localizations.containsKey(key)) {
            return null
        }

        return localizations.getOrDefault(key, "No value at this position!")
    }

    /**
     * Returns the string at the given key, and replaces the placeholders
     *
     * @param key The key for the needed string
     * @param replacements Optional, the replacements
     *
     * @return Null if there isn't any message by this key, else the parsed string
     *
     * @see SkyLocalizer.getRaw
     */
    fun get(key: String, vararg replacements: Replacement): Any? {
        if (!localizations.containsKey(key)) {
            return null
        }

        return localeAdapter.convert(replace(localizations.getOrDefault(key, "No value at this position!"), *replacements))
    }

    /**
     * Replaces the placeholders in the given string with the given replacements
     *
     * @param message The message where the placeholders are in
     * @param replacements The replacements for the message
     *
     * @return The translated string
     */
    fun replace(message: String, vararg replacements: Replacement): String {
        var replacedMessage = message

        replacements.forEach {
            replacedMessage = replacedMessage.replace(placeholderPrefix + it.placeholder + placeholderSuffix, it.replacement)
        }

        return replacedMessage
    }

    fun getLocalizations(): Map<String, String> {
        return localizations
    }
}