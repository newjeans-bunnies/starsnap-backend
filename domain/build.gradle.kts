plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":exception"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}