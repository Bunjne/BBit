@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.buildKonfig) apply false
    alias(libs.plugins.room) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
