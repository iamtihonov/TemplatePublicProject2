plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.network"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.androidx.ktx)
    implementation(libs.datetime)

    // Retrofit (rules added)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.logging.interceptor)

    // Chuck (no rules needed)
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    implementation(project(":core:utils"))
}
