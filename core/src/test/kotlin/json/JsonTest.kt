package json

import com.google.gson.Gson
import de.skyslycer.skylocalizer.utils.JsonUtils

fun main() {
    val map: MutableMap<*, *> = Gson().fromJson(
        """{"foo": {"bar": {"stab": "yes", "lel": "2"}}}""", MutableMap::class.java
    )

    println(JsonUtils.getJsonObjectsFromMap(null, map))
}