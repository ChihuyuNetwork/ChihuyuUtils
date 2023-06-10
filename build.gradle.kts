plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    `maven-publish`
}

group = "love.chihuyu"
version = "1.0.0-SNAPSHOT"
val pluginVersion: String by project.ext

repositories {
    mavenCentral()
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:$pluginVersion-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
}

ktlint {
    ignoreFailures.set(true)
    disabledRules.add("no-wildcard-imports")
}

tasks {
    test {
        useJUnitPlatform()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets.main.get().resources.srcDirs) {
            filter(org.apache.tools.ant.filters.ReplaceTokens::class, mapOf("tokens" to mapOf(
                "version" to project.version.toString(),
                "name" to project.name,
                "mainPackage" to "love.chihuyu.${project.name.lowercase()}.${project.name}"
            )))
            filteringCharset = "UTF-8"
        }
    }

    shadowJar {
        exclude("org/slf4j/**")
    }
}

publishing {
    repositories {
        maven {
            name = "repo"
            credentials(PasswordCredentials::class)
            url = uri(
                if (project.version.toString().endsWith("SNAPSHOT"))
                    "https://repo.hirosuke.me/repository/maven-snapshots/"
                else
                    "https://repo.hirosuke.me/repository/maven-releases/"
            )
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

kotlin {
    jvmToolchain(17)
}

open class SetupTask : DefaultTask() {

    @TaskAction
    fun action() {
        val projectDir = project.projectDir
        val srcDir = projectDir.resolve("src/main/kotlin/love/chihuyu/${project.name.lowercase()}").apply(File::mkdirs)
        srcDir.resolve("${project.name}Plugin.kt").writeText(
            """
                package love.chihuyu.${project.name.lowercase()}
                
                import org.bukkit.plugin.java.JavaPlugin

                class ${project.name}Plugin: JavaPlugin() {
                    companion object {
                        lateinit var ${project.name}Plugin: JavaPlugin
                    }
                
                    init {
                        ${project.name}Plugin = this
                    }
                }
            """.trimIndent()
        )
    }
}

task<SetupTask>("setup")