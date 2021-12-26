plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = "de.skyslycer.skylocalizer"
version = "1.2"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(kotlin("stdlib"))

    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    api(project(":core"))
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        val classifier : String? = null
        archiveClassifier.set(classifier)
    }

    build {
        dependsOn("shadowJar")
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    val publishData = PublishData(project)
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        groupId = project.group as String?
        artifactId = project.name
        version = publishData.getVersion()
    }

    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }

            name = "SkyNexus"
            url = uri(publishData.getRepository())
        }
    }
}

class PublishData(private val project: Project) {
    var type: Type = getReleaseType()
    var hashLength: Int = 7

    private fun getReleaseType(): Type {
        val branch = getCheckedOutBranch()
        return when {
            branch.contentEquals("master") -> Type.RELEASE
            branch.startsWith("dev") -> Type.DEV
            else -> Type.SNAPSHOT
        }
    }

    private fun getCheckedOutGitCommitHash(): String = System.getenv("GITHUB_SHA")?.substring(0, hashLength) ?: "local"

    private fun getCheckedOutBranch(): String = System.getenv("GITHUB_REF")?.replace("refs/heads/", "") ?: "local"

    fun getVersion(): String = getVersion(false)

    fun getVersion(appendCommit: Boolean): String =
        type.append(getVersionString(), appendCommit, getCheckedOutGitCommitHash())

    private fun getVersionString(): String = (project.version as String).replace("-SNAPSHOT", "").replace("-DEV", "")

    fun getRepository(): String = type.repo

    enum class Type(private val append: String, val repo: String, private val addCommit: Boolean) {
        RELEASE("", "https://repo.skyslycer.de/repository/maven-releases/", false),
        DEV("-DEV", "https://repo.skyslycer.de/repository/maven-dev/", true),
        SNAPSHOT("-SNAPSHOT", "https://repo.skyslycer.de/repository/maven-snapshots/", true);

        fun append(name: String, appendCommit: Boolean, commitHash: String): String =
            name.plus(append).plus(if (appendCommit && addCommit) "-".plus(commitHash) else "")
    }
}

