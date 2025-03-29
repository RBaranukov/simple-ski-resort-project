import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.jooq.meta.jaxb.Configuration

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ski-resort-Baranukov"
java.sourceCompatibility = JavaVersion.VERSION_21

plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.3.4"
    id("org.jooq.jooq-codegen-gradle") version "3.19.15"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-cache")
    api("org.springframework.kafka:spring-kafka")
    api("com.github.ben-manes.caffeine:caffeine")
    api("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    compileOnly("org.projectlombok:lombok")
}

sourceSets {
    
}

tasks.register("jooqCodegen") {
    doLast {
        Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl("jdbc:postgresql://localhost:5432/postgres")
                    .withUser("postgres")
                    .withPassword("password")
            )
            .withGenerator(
                Generator()
                    .withDatabase(Database().withInputSchema("public"))
                    .withTarget(
                        Target()
                            .withPackageName("org.jooq.generated")
                            .withDirectory("${layout.projectDirectory}/src/main/java")
                    )
            ).also(GenerationTool::generate)
    }
}


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
