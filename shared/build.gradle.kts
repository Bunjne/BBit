@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.sqldelight)
//    alias(libs.plugins.buildKonfig)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export(libs.mvvm.core)
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

    android.libraryVariants.all {
        this.javaCompileProvider.get().destinationDirectory
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(libs.bundles.ktor.common)
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
                api(libs.mvvm.compose)
                api(libs.mvvm.core)
                api(libs.mvvm.flow)
                // Navigation
                implementation(libs.bundles.voyager.common)
                // use api since the desktop app need to access the Cef to initialize it.
                api(libs.compose.webview.multiplatform)
                implementation(libs.multiplatform.settings)
                // Local Storage
                implementation(libs.androidx.dataStore.core)
                implementation(libs.androidx.datastore.preferences)
                // TouchLab for iOS running
                implementation(libs.stately.common)
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
                implementation(libs.stately.isolate)
//                implementation("co.touchlab:stately-iso-collections")
            }
        }
    }
}

android {
    namespace = "com.bunjne.bbit"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        buildConfig = true
    }

//    flavorDimensions.add("variant")
//    productFlavors {
//        create("dev") {
//            dimension = "variant"
//            isDefault = true
//        }
//
//        create("staging") {
//            dimension = "variant"
//        }
//
//        create("prod") {
//            dimension = "variant"
//        }
//    }
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