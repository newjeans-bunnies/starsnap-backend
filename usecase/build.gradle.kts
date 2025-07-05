plugins {
    kotlin("jvm")
}

val jjwtVersion = "0.11.5"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")

    implementation(project(":domain"))
    testImplementation(kotlin("test"))
}



kotlin {
    jvmToolchain(17)
}