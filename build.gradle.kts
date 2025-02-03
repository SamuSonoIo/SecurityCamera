import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.4"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "org.samu"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.flyte.gg/releases")

}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")

    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.brigadier:4.0.0-beta.19")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.21")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.21")
    implementation ("com.cohere:cohere-java:+")
    implementation("gg.flyte:twilight:1.1.17")
}

val targetJavaVersion = 17
kotlin {

    compilerOptions {
        javaParameters = true
    }

    jvmToolchain(targetJavaVersion)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.20.1")

    }

    shadowJar {
        minimize()
    }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition", "-Xmx3G")
}

kotlin {
    compilerOptions {
        javaParameters = true
    }
}

tasks.withType<KotlinJvmCompile> {
    compilerOptions {
        javaParameters = true
    }
}

bukkit {
    main = "org.samu.securityCamera.SecurityCamera"
    apiVersion = "1.20"

    name = getName()
    version = getVersion().toString()
    author = "samusonoio"
}