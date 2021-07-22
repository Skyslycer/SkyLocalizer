package json

import com.google.gson.Gson
import de.skyslycer.skylocalizer.utils.MapUtils

fun main() {
    val map: MutableMap<*, *> = Gson().fromJson(
        """{"foo": {"bar": {"stab": "yes", "lel": "2"}}}""", MutableMap::class.java
    )

    println(MapUtils.getObjectsFromMap(null, map))
}