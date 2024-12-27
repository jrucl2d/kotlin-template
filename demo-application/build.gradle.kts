tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    val jakartaValidationApiVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationApiVersion")
    implementation("org.springframework:spring-tx") // for transaction

    implementation(project(":demo-component"))
    testImplementation(testFixtures(project(":demo-component")))
}
