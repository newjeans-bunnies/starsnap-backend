plugins {
    id("org.springframework.boot") version "3.0.5"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

val jjwtVersion = "0.11.5"

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")


    implementation("com.bucket4j:bucket4j-core:8.3.0")


    // JWT
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    implementation(project(":adapter-usecase"))
    implementation(project(":usecase"))
    implementation(project(":adapter-infrastructure"))
    implementation(project(":exception"))
    implementation(project(":domain"))
    implementation(project(":config"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}