plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.nightfilter.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nightfilter.app"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core:1.10.1")
    implementation("com.google.android.material:material:1.9.0")
}
