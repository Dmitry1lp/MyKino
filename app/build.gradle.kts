plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.practicum.mykino"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.practicum.mykino"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.koin.android)
    implementation(libs.kotlin.stdlib)
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl:4.3.2")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:4.3.2")

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.5")
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation (libs.retrofit2.retrofit)
    implementation(libs.material)
    implementation (libs.gson)
    implementation (libs.com.github.bumptech.glide.glide2)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    annotationProcessor (libs.compiler)
    implementation (libs.squareup.converter.gson)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}