import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
    kotlin("plugin.allopen") version "1.9.0"
    kotlin("kapt") version "1.9.0"
    id("java")
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springframework.boot") version "3.1.2"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("java")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }
    allOpen {
      annotation("jakarta.persistence.Entity")
    }
    dependencies {
        if (name.endsWith("service")) {
            runtimeOnly("com.mysql:mysql-connector-j")
            implementation("org.hibernate:hibernate-spatial:6.3.0.Final")
            implementation("org.springframework.boot:spring-boot-starter-data-jpa")
            implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
            testImplementation(project(":weatherfit-api"))
        }
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation ("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation(kotlin("test"))
        testImplementation(project(":weatherfit-core"))
    }
    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(17)
    }
}