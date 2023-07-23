plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "love.chihuyu"
version = "1.1.0-SNAPSHOT"
val pluginVersion: String by project.ext

repositories {
    mavenCentral()
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:$pluginVersion-R0.1-SNAPSHOT")
    compileOnly(kotlin("stdlib"))
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
                    "https://repo.hirosuke.me/snapshots"
                else
                    "https://repo.hirosuke.me/releases"
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

task("setup") {
    doFirst {
        val projectDir = project.projectDir
        projectDir.resolve("renovate.json").deleteOnExit()
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

task("generateActionsFile") {
    doFirst {
        val actionFile = projectDir.resolve(".github/workflows").apply(File::mkdirs)
        actionFile.resolve("deploy.yml").writeText(
            """
                name: Deploy
                on:
                  workflow_dispatch:
                  push:
                    branches:
                      - 'master'
                    paths-ignore:
                      - "**.md"
                jobs:
                  build:
                    runs-on: ubuntu-latest
                    permissions:
                      contents: read
                    steps:
                      - uses: actions/checkout@v3
                      - name: Set up JDK 17
                        uses: actions/setup-java@v3
                        with:
                          java-version: '17'
                          distribution: 'temurin'
                      - name: Grant execute permission for gradlew
                        run: chmod +x gradlew
                      - name: Prepare gradle.properties
                        run: |
                          mkdir -p ${ "\$HOME" }/.gradle
                          echo ${ "repoUsername=\${{ secrets.DEPLOY_USERNAME }} "} >> ${ "\$HOME" }/.gradle/gradle.properties
                          echo ${ "repoPassword=\${{ secrets.DEPLOY_PASSWORD }}"} >> ${ "\$HOME" }/.gradle/gradle.properties
                      - name: Deploy
                        run: |
                          ./gradlew clean test publish
            """.trimIndent()
        )
    }
}