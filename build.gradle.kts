plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.mysite"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly ("com.h2database:h2")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok:1.18.36")
	testImplementation ("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
	implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation ("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
// https://mvnrepository.com/artifact/org.commonmark/commonmark
	implementation("org.commonmark:commonmark:0.24.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
