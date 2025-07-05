plugins {
    id("org.springframework.boot") version "3.0.5"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

val jjwtVersion = "0.11.5"

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":adapter-infrastructure"))
    implementation(project(":usecase"))
    implementation(project(":domain"))
    implementation(project(":exception"))
    implementation(project(":config"))

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    implementation("io.viascom.nanoid:nanoid:1.0.1")

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(platform("software.amazon.awssdk:bom:2.29.45"))
    implementation("software.amazon.awssdk:s3")

    implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}