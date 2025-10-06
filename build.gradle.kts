import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// The libraries are currently published to JitPack. JitPack picks up the
// version from the repo label, resulting in all libraries from the repo
// having the same version in JitPack. Setting the version for all projects
// conveys this.
allprojects {
    group = "org.tree-ware.tree-ware-kotlin-e2e-shell"
    version = "0.6.0.0"
}

val hikariCpVersion = "5.0.1"
val log4j2Version = "2.19.0"

plugins {
    kotlin("jvm") version "2.1.10"
    id("idea")
    id("org.tree-ware.core") version "0.6.0.0-SNAPSHOT"
    id("java")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    mavenLocal()
}

treeWare {
    metaModelAux {
        auxClasses.add("org.treeWare.mySql.aux.MySqlMetaModelMapAuxPlugin")
    }
}

dependencies {
    implementation(libs.treeWareKotlinServer)
    implementation(libs.treeWareKotlinMysql)
    implementation(libs.treeWareKotlinCore)

    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4j2Version")

    implementation(kotlin("stdlib"))
}