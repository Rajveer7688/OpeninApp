plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "openin.app.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "openin.app.android"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.sdp.android) //Text Dimension
    implementation(libs.material) //UI Design
    implementation(libs.retrofit) //Retrofit
    implementation(libs.converter.gson) //Gson
    implementation(libs.logging.interceptor) //Logging
    implementation(libs.kotlinx.coroutines.android) //Coroutines
    implementation(libs.androidx.lifecycle.livedata.ktx) //Lifecycle Components
    implementation(libs.androidx.lifecycle.viewmodel.ktx) //Lifecycle ViewModel
    implementation(libs.androidx.databinding.runtime) //DataBinding
    implementation(libs.mpandroidchart) //Chart
    implementation(libs.glide) //Image Loading
}