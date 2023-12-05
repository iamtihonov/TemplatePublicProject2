plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.datastore"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.androidx.ktx)
    api(libs.datastore)

    implementation(project(":core:utils"))
}
