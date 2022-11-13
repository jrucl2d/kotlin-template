tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":demo-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
