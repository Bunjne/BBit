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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            binaryOption("bundleId", "com.bunjne.bbit.shared")
        }
    }

    android.libraryVariants.all {
        this.javaCompileProvider.get().destinationDirectory
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Apply compose resources such as, string.xml
                implementation(compose.components.resources)

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(libs.bundles.ktor.common)
                implementation(libs.kotlinx.datetime)
                // DI
                implementation(project.dependencies.platform(libs.koin.bom))
                api(libs.koin.core)
                implementation(libs.bundles.koin.compose)
                implementation(libs.koin.test)
                // Compose UI
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.accompanist.system.ui.controller)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.lifecycle.viewmodel)
                // Navigation
                implementation(libs.navigation.compose)
                // use api since the desktop app need to access the Cef to initialize it.
                api(libs.compose.webview.multiplatform)
                implementation(libs.multiplatform.settings)
                // Local Storage
                implementation(libs.androidx.dataStore.core)
                implementation(libs.androidx.datastore.preferences)
                // TouchLab for iOS running
                implementation(libs.stately.common)
                // Image Loader
                implementation(libs.ktor.core)
                implementation(libs.coil.compose.core)
                implementation(libs.coil.compose)
                implementation(libs.coil.mp)
                implementation(libs.coil.network.ktor)
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
                implementation(libs.koin.androidx.compose)
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
                implementation(libs.ktor.darwin)
                implementation(libs.sqldelight.ios)
                implementation(libs.stately.isolate)
            }
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "com.bunjne.bbit.resources"
    generateResClass = auto
}

android {
    namespace = "com.bunjne.bbit"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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