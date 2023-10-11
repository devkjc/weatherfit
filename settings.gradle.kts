pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "weatherfit"
include("weatherfit-api")
include("weatherfit-core")
include("weatherfit-user-service")
include("weatherfit-style-service")
include("weatherfit-weather-service")
include("weatherfit-batch")
include("weatherfit-batch")
