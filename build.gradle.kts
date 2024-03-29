plugins {
    kotlin("jvm") version "1.9.22"
    id("idea")
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "indi.nightfish.potato_ip_display"
version = "1.1-bukkit"

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
}

tasks.test {
    useJUnitPlatform()
}

