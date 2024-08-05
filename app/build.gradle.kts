plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zkrallah.z_students"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zkrallah.z_students"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material3:material3:1.2.0-alpha03")
    implementation("androidx.compose.material:material:1.4.3")

    // Lifecycle + ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // ZHttp
    implementation("com.github.muhammadzkralla:ZHttp:2.7")
    implementation("com.google.code.gson:gson:2.10.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Coil
    implementation("io.coil-kt:coil-compose:1.3.1")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:5.2.0")

    // OnBoarding
    implementation("com.google.accompanist:accompanist-pager:0.12.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Date Picker
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.3.0")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.3.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")
}
kapt {
    correctErrorTypes = true
}