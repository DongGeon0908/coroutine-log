import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.22"

    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    idea
}

group = "com.goofy"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

springBoot.buildInfo { properties { } }

object DependencyVersion {
    /** arrow fx */
    const val ARROW_FX_VERSION = "1.2.1"

    /** log */
    const val KOTLIN_LOGGING_VERSION = "6.0.3"
    const val LOGBACK_ENCODER_VERSION = "7.4"

    /** test */
    const val MOCKK_VERSION = "1.13.9"
    const val KOTEST_VERSION = "5.8.0"
    const val KOTEST_EXTENSION_VERSION = "1.1.3"
}

dependencies {
    /** spring starter */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    /** kotlin */
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /** arrow-kt */
    implementation("io.arrow-kt:arrow-fx-coroutines:${DependencyVersion.ARROW_FX_VERSION}")
    implementation("io.arrow-kt:arrow-fx-stm:${DependencyVersion.ARROW_FX_VERSION}")

    /** logger */
    implementation("io.github.oshai:kotlin-logging-jvm:${DependencyVersion.KOTLIN_LOGGING_VERSION}")
    implementation("net.logstash.logback:logstash-logback-encoder:${DependencyVersion.LOGBACK_ENCODER_VERSION}")

    /** etc */
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    /** test **/
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:${DependencyVersion.MOCKK_VERSION}")

    /** kotest */
    testImplementation("io.kotest:kotest-runner-junit5:${DependencyVersion.KOTEST_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${DependencyVersion.KOTEST_VERSION}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${DependencyVersion.KOTEST_EXTENSION_VERSION}")
}

defaultTasks("bootRun")

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "8.5"
}
