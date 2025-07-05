import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}



allprojects {
    group = "com.photo.server.starsnap"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://jitpack.io") }
    }

}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
    }

    tasks.named<BootJar>("bootJar") {
        enabled = false
    }

    tasks.named<Jar>("jar") {
        enabled = true
    }

    tasks.test {
        enabled = false
        useJUnitPlatform()
    }
}