dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-commons")
    implementation(project(":weatherfit-style-service"))
    implementation(project(":weatherfit-weather-service"))
    implementation(project(":weatherfit-feed-service"))
    implementation(project(":weatherfit-user-service"))
    implementation(project(":weatherfit-core"))
}