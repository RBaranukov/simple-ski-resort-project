group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ski-resort-Baranukov"
java.sourceCompatibility = JavaVersion.VERSION_21

plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.3.4"
    id("org.jooq.jooq-codegen-gradle") version "3.19.11"
    id("org.liquibase.gradle") version "2.2.2"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-cache")
    api("org.springframework.kafka:spring-kafka")
    api("com.github.ben-manes.caffeine:caffeine")
    implementation("org.liquibase:liquibase-core")
    implementation("com.typesafe.akka:akka-actor_2.13:2.8.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("info.picocli:picocli:4.7.6")
    jooqCodegen("org.postgresql:postgresql:42.7.3")
    implementation("info.picocli:picocli-codegen:4.7.6")
    implementation("info.picocli:picocli:4.7.6")
}

liquibase {
    activities {
        register("main") {
                arguments = mapOf(
                    "changelogFile" to "src/main/resources/db/changelog/db.changelog-master.xml",
                    "url" to "jdbc:postgresql://localhost:5432/postgres",
                    "username" to "postgres",
                    "password" to "password",
                    "driver" to "org.postgresql.Driver"
                )
        }
    }
    runList = "main"
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/postgres"
            user = "postgres"
            password = "password"
        }

        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                includes = ".*"
            }
        }
    }
}

tasks.named("jooqCodegen") {
    dependsOn(tasks.compileJava)
}

tasks.withType<JavaCompile> {
    dependsOn(tasks.named("update"))
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

tasks.build {
    dependsOn(tasks.named("jooqCodegen"))
}