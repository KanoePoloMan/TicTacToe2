plugins {
    id("java")
    id("org.springframework.boot") version "3.4.0"
}

group = "s21.main"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.boot:spring-boot-starter-web:3.4.0")

    implementation ("org.springframework.boot:spring-boot-starter-security:3.4.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.0")
    implementation("org.postgresql:postgresql:42.7.4")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.4.0")

    implementation("org.springframework.boot:spring-boot-docker-compose:3.4.0")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest.attributes["Main-Class"] = "s21.main.Main"
}
java {
    toolchain.languageVersion = JavaLanguageVersion.of(18)
}