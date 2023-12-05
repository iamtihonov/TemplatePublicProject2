plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.library.compose)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.login"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.androidx.ktx)
}
