import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("net.coobird:thumbnailator:0.4.14")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}