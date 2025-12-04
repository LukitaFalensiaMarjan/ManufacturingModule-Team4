plugins {
    kotlin("jvm") version "2.2.0" // Pastikan versi sesuai
    id("io.ktor.plugin") version "3.0.0" // Plugin Ktor terbaru
    kotlin("plugin.serialization") version "2.1.0"
}

group = "com.trin.erp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 1. Ktor Server (API)
    implementation("io.ktor:ktor-server-core:3.0.0")
    implementation("io.ktor:ktor-server-netty:3.0.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
    implementation("io.ktor:ktor-server-cors:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // 2. Database (Exposed ORM + PostgreSQL)
    implementation("org.jetbrains.exposed:exposed-core:0.56.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.56.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.56.0")
    implementation("org.postgresql:postgresql:42.7.2") // Driver PostgreSQL Baru

    // 3. Testing
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24) // Menggunakan JDK 24 sesuai request
}