tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    api(project(":demo-domain"))

    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation(testFixtures(project(":demo-domain")))
}
