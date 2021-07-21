package de.skyslycer.skylocalizer.adapter

class StringAdapter : LocaleAdapter {
    override fun convert(string: String): Any {
        return string
    }
}