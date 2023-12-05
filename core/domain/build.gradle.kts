plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.hilt)
    id("com.google.devtools.ksp")
}

android {
    namespace = "ua.good.domain"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.datetime)

    implementation(project(":core:utils"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))
}
