plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.7"
}

group = "org.jetbrains.kotilnx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okio:okio:3.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.7")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

benchmark {
    targets {
        register("main")
    }
}

kotlin {
    jvmToolchain(11)
}