plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.data"
}

dependencies {
    testImplementation(libs.junit.java)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.datetime)

    implementation(project(":core:utils"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":core:db"))
    implementation(project(":core:notifications"))
}
