tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    api(project(":demo-domain"))
    implementation(project(":demo-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter")
}
