plugins {
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.8.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.photo.server.starsnap"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.5")
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":exception"))

    implementation("org.springframework.cloud:spring-cloud-function-context:4.0.5")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:4.0.0")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web:4.0.0")

    implementation(platform("software.amazon.awssdk:bom:2.29.45"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:lambda:2.20.7")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")

    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

// Lambda용 Fat JAR 설정
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("lambda-function")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
}

// bootJar 비활성화 (Lambda에선 필요 없음)
tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}

// JAR Manifest에 Start-Class 명시
tasks.withType<Jar> {
    manifest {
        attributes["Start-Class"] = "com.photo.server.starsnap.adapter_lambda_s3.StarsnapLambdaApplication"
    }
}

// build 시 shadowJar 포함
tasks.build {
    dependsOn(tasks.shadowJar)
}