package de.skyslycer.skylocalizer

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import de.skyslycer.skylocalizer.reader.LocaleReader
import de.skyslycer.skylocalizer.utils.MapUtils
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class JsonReader : LocaleReader {
    private val gson = Gson()

    override fun read(reader: Any): Map<String, String> {
        val bufferedReader: BufferedReader = when (reader) {
            is BufferedReader -> reader
            is Path -> Files.newBufferedReader(reader)
            is File -> Files.newBufferedReader(reader.toPath())
            is InputStream -> reader.bufferedReader()
            else -> throw UnhandledReaderTypeException("Type ${reader.javaClass.name} can't be handled in ${this.javaClass.name}!")
        }

        return try {
            readLocale(bufferedReader)
        } catch (exception: Exception) {
            mutableMapOf()
        }
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    private fun readLocale(reader: BufferedReader): Map<String, String> {
        return MapUtils.getObjectsFromMap(null, gson.fromJson(reader, Map::class.java))
    }
}