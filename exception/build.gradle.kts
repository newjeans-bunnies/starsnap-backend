plugins {
    id("org.springframework.boot") version "3.0.5"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}