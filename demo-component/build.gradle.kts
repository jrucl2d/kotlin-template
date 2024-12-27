tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    implementation(project(":demo-infrastructure"))
}

plugins {
    `java-test-fixtures`
}
