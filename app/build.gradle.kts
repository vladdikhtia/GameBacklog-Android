
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "nl.hva.madlevel6task1"
    compileSdk = 34

    defaultConfig {
        applicationId = "nl.hva.madlevel6task1"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // For swiping
    implementation("androidx.compose.material:material:1.5.0")
    // level3
    val navVersion = "2.7.1"
    implementation ("androidx.navigation:navigation-compose:$navVersion")
    // First time in level 4 example.
    val liveDataVersion = "1.3.0-rc01"
    implementation ("androidx.compose.runtime:runtime-livedata:$liveDataVersion")

    //network libraries
// First time in level 4 example, and only in level 4.
    implementation ("com.squareup.retrofit2:retrofit:2.8.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.8.1")
    implementation ("com.squareup.okhttp3:okhttp:4.4.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.4.0")

    // Bottom navigation
    implementation("androidx.compose.material:material:1.5.0")

// image loading
    implementation("io.coil-kt:coil:2.1.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    // TODO: Add the dependencies for Firebase products you want to use
    //  (// implementation 'com.google.firebase:firebase-firestore-ktx:24.7.1' IS AUTOMATICALLY ADDED LATER WITH CORRECT RELEASE)
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries
    implementation("com.google.firebase:firebase-database-ktx")

    // Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Room - Level 6
    val roomVersion = "2.6.0"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")


    implementation("androidx.room:room-ktx:$roomVersion")
}