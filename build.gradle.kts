import io.swagger.v3.oas.models.servers.Server
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	id("com.epages.restdocs-api-spec") version "0.19.2"

	kotlin("jvm") version "1.9.24"
	kotlin("kapt") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("plugin.allopen") version "1.9.24"
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

val kotestVersion = "5.9.1"
val kotestSpringVersion = "1.3.0"
val mockkVersion = "1.13.10"
val springMockkVersion = "4.0.2"
val kotlinLoggingVersion = "6.0.9"
val jjwtVersion = "0.12.5"
val jdslVersion = "3.4.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")
	implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")

	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${jdslVersion}")
	implementation("com.linecorp.kotlin-jdsl:jpql-render:${jdslVersion}")
	implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${jdslVersion}")

	testRuntimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "org.mockito")
	}
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("com.ninja-squad:springmockk:$springMockkVersion")

	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.2")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
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
