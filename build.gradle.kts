plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "com.photo.server.starsnap"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

repositories {
    mavenCentral()
}

repositories {
    mavenCentral()
}

val jjwtVersion = "0.11.5"

val coroutinesVersion = "1.8.1"
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

dependencyManagement {

    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}

dependencies {

    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("com.google.api-client:google-api-client-jackson2:2.2.0")
    implementation("com.google.api-client:google-api-client:2.2.0")

    implementation("ognl:ognl:3.3.4")

    developmentOnly("org.springframework.boot:spring-boot-devtools:3.2.2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.viascom.nanoid:nanoid:1.0.1")

    // s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.261")

    implementation("com.bucket4j:bucket4j-core:8.3.0")


    // test code
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
