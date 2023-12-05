plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
    id("com.google.devtools.ksp")
}

android {
    namespace = "ua.good.db"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.datetime)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(project(":core:utils"))
}
