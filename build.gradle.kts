//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//plugins {
//    kotlin("jvm") version "1.7.21"
//    application
//}
//
//group = "dev.misasi.giancarlo"
//version = "1.0-SNAPSHOT"
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    testImplementation(kotlin("test"))
//}
//
//tasks.test {
//    useJUnitPlatform()
//}
//
//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}
//
//application {
//    mainClass.set("MainKt")
//}
/*
 * MIT License
 *
 * Copyright (c) 2022 Giancarlo Misasi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

import org.gradle.internal.os.OperatingSystem

group = "dev.misasi.giancarlo"
version = "1.0-SNAPSHOT"

// Versions
val kotlinVersion = "1.8.0"
val coroutinesVersion = "1.6.4"

plugins {
    kotlin("multiplatform") version "1.8.0"
    id("maven-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                // Using maven local repository:
                // https://github.com/giancarlo-misasi/kotlin-game-engine-lib
                implementation("dev.misasi.giancarlo:kotlin-game-engine:1.0.0")
            }
        }
        val commonTest by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                dependsOn(commonMain)
                // Using maven local repository:
                // https://github.com/giancarlo-misasi/kotlin-game-engine-lib
                implementation("dev.misasi.giancarlo:kotlin-game-engine-jvm:1.0.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                dependsOn(jvmMain)
                implementation(kotlin("test"))
            }
        }
    }
}
