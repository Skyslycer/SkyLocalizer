package de.skyslycer.skylocalizer

import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import de.skyslycer.skylocalizer.reader.LocaleReader
import de.skyslycer.skylocalizer.utils.MapUtils
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class BukkitYamlReader : LocaleReader {
    override fun read(reader: Any): Map<String, String> {
        val yaml: FileConfiguration = YamlConfiguration()

        val bufferedReader: BufferedReader = when (reader) {
            is BufferedReader -> reader
            is Path -> Files.newBufferedReader(reader)
            is File -> Files.newBufferedReader(reader.toPath())
            is InputStream -> reader.bufferedReader()
            else -> throw UnhandledReaderTypeException("Type ${reader.javaClass.name} can't be handled in ${this.javaClass.name}!")
        }

        return try {
            yaml.load(bufferedReader)
            MapUtils.checkMapAndMapEntriesToString(yaml.getValues(true))
        } catch (exception: Exception) {
            mutableMapOf()
        }
    }
}