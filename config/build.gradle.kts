plugins {
    id("org.springframework.boot") version "3.0.5"

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

val jjwtVersion = "0.11.5"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("com.bucket4j:bucket4j-core:8.3.0")

    // AWS SDK (필요시)
    implementation(platform("software.amazon.awssdk:bom:2.29.45"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:lambda:2.20.7")

    implementation("io.github.openfeign:feign-core:12.3")
    implementation("io.github.openfeign:feign-slf4j:12.3")

    // aws sqs
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    implementation(project(":usecase"))
    implementation(project(":domain"))
    implementation(project(":exception"))

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}