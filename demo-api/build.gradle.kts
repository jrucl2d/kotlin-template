tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":demo-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

    testImplementation(testFixtures(project(":demo-domain")))
}
