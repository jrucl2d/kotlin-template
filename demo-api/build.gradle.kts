tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    val jakartaValidationApiVersion: String by project

    implementation(project(":demo-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationApiVersion")

    testImplementation(testFixtures(project(":demo-domain")))
}
