import com.codingfeline.buildkonfig.compiler.FieldSpec

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.cocoapods)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

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
                //TODO Remove after uuid is added in Koin with a new version
                // https://github.com/InsertKoinIO/koin/issues/2003
                api("com.benasher44:uuid:0.8.4")// DI
                implementation(project.dependencies.platform(libs.koin.bom))
                api(libs.koin.core)
                implementation(libs.bundles.koin.compose)
                implementation(libs.koin.test)
                // Compose UI
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.adaptive)
                implementation(libs.adaptive.layout)
                implementation(libs.adaptive.navigation)
                implementation(libs.material3.adaptive.navigation.suite)
                implementation(libs.material3.windowSizeClass)
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

                // Local data source
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
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
                implementation(libs.stately.isolate)
                implementation(libs.ktor.darwin)
            }
        }
    }

    compilerOptions {
        // Common compiler options applied to all Kotlin source sets
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "14.1"

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.androidx.room.complier)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

buildkonfig {
    packageName = "com.bunjne.bbit"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "BITBUCKET_CLIENT_ID",
            value = getEnvOrProperty("BITBUCKET_CLIENT_ID")

        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "BITBUCKET_CLIENT_KEY",
            value = getEnvOrProperty("BITBUCKET_CLIENT_KEY")
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "BITBUCKET_AUTH_CALLBACK_URL",
            value = getEnvOrProperty("BITBUCKET_AUTH_CALLBACK_URL")
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "BASE_URL",
            value = getEnvOrProperty("BASE_URL")
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "AUTH_BASE_URL",
            value = getEnvOrProperty("AUTH_BASE_URL")
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "SPACEX_URL",
            value = getEnvOrProperty("SPACEX_URL")
        )
    }
}

/**
 * This function gets the value of an environment variable or property respectively.
 *
 * Note: use [property] instead of [findProperty] to throw an exception if not found.
 * @return an environment variable or property respectively.
 * */
private fun getEnvOrProperty(key: String): String? =
    System.getenv(key) ?: (property(key) as? String)