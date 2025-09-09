import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// The libraries are currently published to JitPack. JitPack picks up the
// version from the repo label, resulting in all libraries from the repo
// having the same version in JitPack. Setting the version for all projects
// conveys this.
allprojects {
    group = "org.tree-ware.tree-ware-kotlin-e2e-shell"
    version = "0.5.1.0"
}

val hikariCpVersion = "5.0.1"
val ktorVersion = "3.1.1" // TODO(cleanup): these should come from tree-ware-kotlin-server as api() dependencies
val slf4jSimpleVersion = "2.0.17"

plugins {
    kotlin("jvm") version "2.1.10"
    id("idea")
    id("org.tree-ware.core") version "0.5.1.0"
    id("java")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.treeWareKotlinServer)
    implementation(libs.treeWareKotlinMysql)
    implementation(libs.treeWareKotlinCore)

    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")

    implementation(kotlin("stdlib"))
}