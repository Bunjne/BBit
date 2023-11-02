@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight")
    id("org.jetbrains.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    ios()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val mokoMvvmVersion = "0.13.0"
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.core)
                //implementation(libs.ktor.cio)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.negotiation)
                implementation(libs.ktor.serilization)
                implementation(libs.kotlinx.datetime)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.test)
                //put your multiplatform dependencies here
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.accompanist.system.ui.controller)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(libs.kotlinx.coroutines.core)
                api(libs.mvvm.core)
                api(libs.mvvm.flow)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.koin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.okhttp)
                implementation(libs.sqldelight.android)
                implementation(libs.koin.compose)
                implementation(libs.koin.androidx.compose)
                api(libs.mvvm.flow.compose)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.ktor.darwin)
                implementation(libs.sqldelight.ios)
                implementation(libs.mvvm.core)
                implementation(libs.mvvm.flow)
            }
        }
    }
}

android {
    namespace = "com.bunjne.bbit"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.bunjne.bbit")
        }
    }
}

dependencies {
    implementation("androidx.core:core:1.10.1")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
}