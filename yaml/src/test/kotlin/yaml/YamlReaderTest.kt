package yaml

import de.skyslycer.skylocalizer.YamlReader

fun main() {
    println(YamlReader().read(YamlReaderTest::class.java.classLoader.getResourceAsStream("test.yml")!!))
}

class YamlReaderTest