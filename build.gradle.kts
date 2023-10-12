import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpClient
import java.time.Duration.ofSeconds

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.graalvm.buildtools.native") version "0.9.27"
    application
}

group = "com.navercorp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")

}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}

val appName = "graalvm-native-sample"
val filePath = "distributions/graalvm-native-sample.zip"
val httpClient: HttpClient = HttpClient.newBuilder().connectTimeout(ofSeconds(10)).build()

graalvmNative {
    binaries {
        named("main") {
            imageName.set(appName)
            buildArgs.add("--initialize-at-build-time=property.CliProperties")
            buildArgs.add("--enable-http")
            buildArgs.add("-R:MinHeapSize=500m")
            buildArgs.add("-R:MaxHeapSize=500m")
            systemProperties.put("appName", appName)
            resources.autodetect()
        }
    }
}

tasks.register<Zip>("zipCli") {
    archiveFileName.set("graalvm-native-sample.zip")
    from("build/native/nativeCompile/graalvm-native-sample")
}

tasks.register("publish") {
    val baseUrl = "artifactory-url"

    val request = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl))
        .PUT(HttpRequest.BodyPublishers.ofFile(layout.buildDirectory.file(filePath).get().asFile.toPath())).build()

    httpClient.send(request, HttpResponse.BodyHandlers.discarding())
}
