plugins {
    kotlin("jvm") version "1.8.0"
    id("org.graalvm.buildtools.native") version "0.9.27"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}

val appName = "graalvm-native-sample"
graalvmNative {
    binaries {
        named("main") {
            imageName.set(appName)
            buildArgs.add("--initialize-at-build-time=CliProperties")
            buildArgs.add("-H:ReflectionConfigurationFiles=../../resources/main/reflect-config.json")
            buildArgs.add("--enable-http")
            buildArgs.add("-R:MinHeapSize=500m")
            buildArgs.add("-R:MaxHeapSize=500m")
            systemProperties.put("appName", appName)
        }
    }
}