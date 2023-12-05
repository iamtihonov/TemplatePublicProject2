plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.library.compose)
    alias(libs.plugins.template.android.hilt)
}

android {
    namespace = "ua.good.utils"
}

dependencies {
    // Ломают метод viewModels
    // implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    // implementation("com.google.firebase:firebase-analytics-ktx")

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Timber (no rules needed)
    api(libs.timber)
}
