plugins {
    kotlin("jvm") version "1.9.22"
    id("app.cash.sqldelight") version "2.0.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Logger
    implementation("org.lighthousegames:logging:1.3.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    // SQLDelight para SQLite
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
    // Result ROP
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")
    // Serialización JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // Test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


// Configuración del plugin de SqlDeLight
sqldelight {
    databases {
        // Nombre de la base de datos
        create("AppDatabase") {
            // Paquete donde se generan las clases
            packageName.set("dev.joseluisgs.database")
        }
    }
}