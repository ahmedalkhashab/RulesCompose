import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group = "com.example"
val jarVersion = "1.0.45"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.pinterest.ktlint:ktlint-rule-engine:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-rule-engine-core:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-core:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-checkstyle:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-json:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-html:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-plain:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-cli-reporter-sarif:1.3.1")
    implementation("com.pinterest.ktlint:ktlint-ruleset-standard:1.3.1")
    testImplementation("com.pinterest.ktlint:ktlint-test:1.3.1")
    testImplementation("junit:junit:4.13.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


tasks.withType<Jar> {
    // Set strategy to handle duplicate files within the jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Define attributes for the manifest of the jar
    manifest {
        attributes(
            "Implementation-Title" to "stc Custom Ktlint Rules",
            "Implementation-Version" to jarVersion,
        )
    }

    // Collect the runtime dependencies and include them in the jar file
    from({
        configurations["runtimeClasspath"].map { if (it.isDirectory) it else zipTree(it) }
    })
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])

            pom {
                name.set("stc Custom Ktlint Rules")
                description.set("A set of custom ktlint rules for formatting Kotlin code")
                url.set("https://github.com/ahmedalkhashab/RulesCompose") // Update with your repo URL

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("ahmedalkhashab")
                        name.set("ahmed alkhashab")
                        email.set("a.khashab88@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/ahmedalkhashab/RulesCompose.git")
                    developerConnection.set("scm:git:ssh://github.com/ahmedalkhashab/RulesCompose.git")
                    url.set("https://github.com/ahmedalkhashab/RulesCompose")
                }
            }
        }
    }
}
