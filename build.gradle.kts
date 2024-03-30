plugins {
    kotlin("jvm") version "1.9.22"
    id("idea")
    id ("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "indi.nightfish.potato_ip_display"
version = "1.2-bukkit"

repositories {
    mavenCentral()
    maven ("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")


    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.4")
    implementation("org.lionsoul:ip2region:2.7.0")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.4")
    }
}