@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    // alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.bunjne.bbit.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.bunjne.bbit.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        register("benchmark") {
            isDebuggable = false
            matchingFallbacks.add("release")
        }
    }
    flavorDimensions += "mode"
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
//            buildConfigField(type = "String", name = "API_URL", value = "\"https://api.bitbucket.org/2.0/\"")
//            buildConfigField(type = "String", name = "AUTH_BASE_URL", value = "\"https://bitbucket.org/site/oauth2/\"")
//            buildConfigField(type = "String", name = "BITBUCKET_CLIENT_ID", value = "\"YW5N4Zsq76DyKRrBHj\"")
//            buildConfigField(type = "String", name = "BITBUCKET_CLIENT_KEY", value = "\"FBd57pSKYgwRRUxyjFdfCgNMXg8ddxy7\"")
        }
        create("prod") {
            applicationIdSuffix = ".prod"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    /*implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.ui.graphics.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)*/
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.metrics)
    implementation(libs.androidx.tracing.ktx)
}