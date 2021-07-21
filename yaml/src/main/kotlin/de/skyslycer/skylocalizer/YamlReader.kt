package de.skyslycer.skylocalizer

import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import de.skyslycer.skylocalizer.reader.LocaleReader
import de.skyslycer.skylocalizer.utils.JsonUtils
import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class YamlReader : LocaleReader {
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

    private fun readLocale(reader: BufferedReader): Map<String, String> {
        return JsonUtils.getJsonObjectsFromMap(null, Yaml().load(reader))
    }
}