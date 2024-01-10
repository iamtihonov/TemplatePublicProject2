plugins {
    alias(libs.plugins.template.android.application)
    alias(libs.plugins.template.android.application.compose)
    alias(libs.plugins.template.android.hilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "ua.artem.template"

    defaultConfig {
        applicationId = "ua.artem.template"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isCrunchPngs = false // Отключает обработку PNG
            isMinifyEnabled = false
            isShrinkResources = false // Сжатие ресурсов отключено
            applicationIdSuffix = ".debug"

            // Что бы библиотеки не обновляли каждый раз Build Id
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
        }

        // Отключаем оптимизацию png для debug сборки
        if (project.hasProperty("debug")) {
            aaptOptions.cruncherEnabled = false
        }
    }

    lint {
        abortOnError = true
        checkTestSources = true
        checkDependencies = true
        ignoreWarnings = false
        checkReleaseBuilds = false
        checkAllWarnings = true
    }
}

/**
 * При добавлении новой библиотеки или обновлении её версии, проверять работу ProGuard.
 * Для этого нужно собрать релизную сборку через пункт меню (Build -> Generate Signed Apk).
 * Обновить файл proguard-rules.pro
 * Перед каждым блоком с зависимостями библиотеки, должен быть указан комментарий с её именем
 * и информация о правилах ProGuard.
 * Так же после добавления библиотеки, проверять, на сколько вырос размер апк,
 * еще можно смотреть размер репозитория через плагин
 * https://chrome.google.com/webstore/detail/github-repository-size/apnjnioapinblneaedefcnopcjepgkci/related
 *
 * Для проверки последней версии библиотеки включить в настройках пункт Settings > Editor >
 * Inspections > "Newer Library Versions Available" и после выполнить проверку Analyze >
 * "Run Inspection By Name" -> "Newer Library Versions Available"
 */
dependencies {
    // Testing (no rules needed)
    androidTestImplementation(libs.junit.android)
    testImplementation(libs.mockito)
    androidTestUtil(libs.orchestrator)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.intents)
    implementation(libs.espresso.idling)
    androidTestImplementation(libs.espresso.contrib) {
        exclude("support-annotations")
        exclude("support-v4")
        exclude("recyclerview-v7")
    }
    androidTestImplementation(libs.uiautomator)

    // Official (no rules needed)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // Leak Canary
    debugImplementation(libs.leakcanary) // (no rules needed)
    implementation(libs.datetime)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":core:domain"))
    implementation(project(":core:utils"))
    implementation(project(":core:model"))

    implementation(project(":feature:login"))
    implementation(project(":feature:repositories"))
}

kapt {
    correctErrorTypes = true
}
