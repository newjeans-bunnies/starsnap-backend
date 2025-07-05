plugins {
    id("org.springframework.boot") version "3.0.5"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.22"
}

val jjwtVersion = "0.11.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    implementation("io.viascom.nanoid:nanoid:1.0.1")

    implementation(project(":usecase"))
    implementation(project(":exception"))
    implementation(project(":config"))
    implementation(project(":domain"))

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}