import java.util.Properties

private lateinit var metadata: Properties

plugins {
    idea
}

fun Project.requireMetadata(): Properties {
    if (!::metadata.isInitialized) {
        metadata = Properties().apply {
            load(rootProject.file("PLUGIN_VERSION").inputStream())
        }
    }
    return metadata
}

fun getMetadata(key: Any): Any {
    return when (key) {
        "version" -> requireMetadata().getProperty("PLUGIN")
        else -> "?"
    }
}

allprojects {
    group = "indi.nightfish.potato_ip_display"
    version = getMetadata("version")
    description = "Display the IP attribution of players."
}