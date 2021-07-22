package de.skyslycer.skylocalizer.reader

import de.skyslycer.skylocalizer.exceptions.UnhandledReaderTypeException
import java.io.IOException
import kotlin.jvm.Throws

interface LocaleReader {
    @Throws(Throwable::class)
    fun read(reader: Any): Map<String, String>
}