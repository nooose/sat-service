import io.swagger.v3.oas.models.servers.Server
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	id("com.epages.restdocs-api-spec") version "0.19.1"

	kotlin("jvm") version "1.9.22"
	kotlin("kapt") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("plugin.allopen") version "1.9.22"
}

val satVersion = "0.0.1"
group = "com.sat"
version = satVersion

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

tasks.named<Jar>("jar") {
	archiveFileName = "application.jar"
}

repositories {
	mavenCentral()
}

val asciidoctorExt = configurations.create("asciidoctorExt") {
	extendsFrom(configurations["testImplementation"])
}

configurations {
	asciidoctorExt
}

val kotestVersion = "5.8.0"
val mockkVersion = "1.13.10"
val springMockkVersion = "4.0.2"
val kotlinLoggingVersion = "6.0.3"
val querydslVersion = dependencyManagement.importedProperties["querydsl.version"]
val jjwtVersion = "0.12.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")
	implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")

	runtimeOnly("com.h2database:h2")
	implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.1")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.asciidoctor {
	dependsOn("test")
	configurations("asciidoctorExt")
	sources {
		include("**/index.adoc")
	}
	baseDirFollowsSourceFile()
}

tasks.register<Copy>("generateRestDocs") {
	dependsOn("asciidoctor")
	val generated = layout.buildDirectory.dir("docs/asciidoc").get().asFile
	from(generated)
	into("src/main/resources/static/docs/restdocs")
}

tasks.register<Test>("testDocument") {
	useJUnitPlatform()
	filter {
		includeTestsMatching("*.documentation.*")
	}
}

project.openapi3 {
	val localServer = Server()
	localServer.url = "http://localhost:8080"
	localServer.description = "로컬 서버"
	setServers(listOf())
	(servers as MutableList).add(localServer)
	title = "API 규격"
	description = "API 규격 문서"
	version = satVersion
	format = "yaml"
}

tasks.register<Copy>("generateOasToSwagger") {
	dependsOn("openapi3")
	val generated = layout.buildDirectory.dir("api-spec/openapi3.yaml").get().asFile
	from(generated)
	into("src/main/resources/static/docs/swagger")
}

tasks.register("generateDocs") {
	dependsOn("generateRestDocs")
	dependsOn("generateOasToSwagger")
}
