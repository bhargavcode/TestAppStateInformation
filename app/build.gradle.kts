plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.todo.task.testapp2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.todo.task.testapp2"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

    /* This packaging specified due to compiler issue*//*
    packaging{
        resources{
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }*/
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.lifecycleViewModel)
    implementation(libs.lifecycleLiveData)
    implementation(libs.coroutines)
    implementation(libs.gson)

//    // Optional testing dependencies
//    androidTestImplementation(libs.hiltTesting)
//    kaptAndroidTest(libs.hiltCompiler)
}
kapt {
    correctErrorTypes = true
}