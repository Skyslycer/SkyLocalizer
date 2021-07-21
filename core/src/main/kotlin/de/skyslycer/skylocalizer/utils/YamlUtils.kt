package de.skyslycer.skylocalizer.utils

class YamlUtils {
    companion object {
        fun getYamlObjectsFromMap(startingKey: String?, map: Map<*, *>): Map<String, String> {
            val newMap = mutableMapOf<String, String>()

            map.forEach {
                var key = it.key
                var value = it.value

                if (key == null || value == null) {
                    return@forEach
                }

                if (value is Map<*, *>) {
                    newMap.putAll(getYamlObjectsFromMap(if (startingKey == null) key.toString() else "$startingKey.$key", value))
                    return@forEach
                }

                key = if (startingKey == null) key.toString() else "$startingKey.$key"
                value = if (value is List<*>) value.joinToString("\n") else value.toString()

                newMap[key] = value
            }

            return newMap
        }
    }
}