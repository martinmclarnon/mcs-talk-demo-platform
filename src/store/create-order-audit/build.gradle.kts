import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

group = "com.demo"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("io.cucumber:cucumber-java:7.11.1")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
	testImplementation("org.mockito:mockito-core:5.5.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
	testImplementation("org.assertj:assertj-core:3.23.1")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
	systemProperty("cucumber.junit-platform.naming-strategy", "long")
}

val cucumberRuntime by configurations.creating {
	extendsFrom(configurations["testImplementation"])
}

task("execute-bdd-tests") {
	dependsOn("assemble", "compileTestJava")
	doLast {
		javaexec {
			mainClass.set("io.cucumber.core.cli.Main")
			classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
			args = listOf("--plugin", "pretty", "--glue", "com.demo.steps", "src/test/resources/features")
		}
	}
}