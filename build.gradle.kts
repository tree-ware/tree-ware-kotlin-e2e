import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// The libraries are currently published to JitPack. JitPack picks up the
// version from the repo label, resulting in all libraries from the repo
// having the same version in JitPack. Setting the version for all projects
// conveys this.
allprojects {
    group = "org.tree-ware.tree-ware-kotlin-e2e-shell"
    version = "0.4.0.0"
}

val hikariCpVersion = "5.0.1"
val ktorVersion = "2.0.2" // TODO(cleanup): these should come from tree-ware-kotlin-server as api() dependencies
val log4j2Version = "2.16.0"

plugins {
    kotlin("jvm") version "1.7.0"
    id("idea")
    id("org.tree-ware.core") version "0.4.0.0"
    id("java")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

tasks.withType<KotlinCompile> {
    // Compile for Java 8 (default is Java 6)
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(libs.treeWareKotlinServer)
    implementation(libs.treeWareKotlinMysql)
    implementation(libs.treeWareKotlinCore)

    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")

    implementation(kotlin("stdlib"))
}