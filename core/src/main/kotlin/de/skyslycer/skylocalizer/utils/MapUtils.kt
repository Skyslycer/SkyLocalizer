package de.skyslycer.skylocalizer.utils

class MapUtils {
    companion object {
        @JvmStatic
        fun checkMapAndMapEntriesToString(map: Map<*, *>): Map<String, String> {
            val newMap = mutableMapOf<String, String>()

            map.forEach {
                var key = it.key
                var value = it.value

                if (key == null || value == null) {
                    return@forEach
                }

                key = key.toString()
                value = value.toString()

                newMap[key] = value
            }

            return newMap
        }
    }
}