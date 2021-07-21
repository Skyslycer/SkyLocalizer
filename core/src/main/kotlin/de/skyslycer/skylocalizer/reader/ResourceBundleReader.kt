package de.skyslycer.skylocalizer.reader

import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class ResourceBundleReader : LocaleReader {
    override fun read(reader: Any): Map<String, String> {
        val bundle: ResourceBundle = when(reader) {
            is ResourceBundle -> reader
            is Path -> PropertyResourceBundle(Files.newInputStream(reader))
            is File -> PropertyResourceBundle(Files.newInputStream(reader.toPath()))
            is InputStream -> PropertyResourceBundle(reader)
            else -> throw UnhandledReaderTypeException("Type ${reader.javaClass.name} can't be handled in ${this.javaClass.name}!")
        }

        return readLocale(bundle)
    }

    private fun readLocale(bundle: ResourceBundle): Map<String, String> {
        val localizations = mutableMapOf<String, String>()

        bundle.keySet().forEach {
            localizations[it] = bundle.getString(it)
        }

        return localizations
    }
}