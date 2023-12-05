plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.notifications"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.androidx.ktx)

    implementation(project(":core:utils"))
    implementation(project(":core:model"))
}
