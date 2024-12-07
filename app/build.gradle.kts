plugins {

    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.example.beatsangeet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.beatsangeet"
        minSdk = 29
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

    implementation ("com.google.android.gms:play-services-auth:21.2.0")
    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")// Adapter for RxJava2 (if you're using RxJava2)

    implementation ("io.coil-kt:coil-compose:2.0.0")

    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation ("com.squareup.moshi:moshi:1.15.1") // Replace with the latest version
    implementation("com.google.accompanist:accompanist-navigation-material:0.31.1-alpha")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")

    implementation("androidx.media:media:1.7.0")
    implementation("androidx.car.app:app:1.4.0")
    implementation ("androidx.car.app:app-automotive:1.2.0")
    implementation( "androidx.appcompat:appcompat:1.4.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.7.1") // For LiveData support
    implementation ("androidx.compose.runtime:runtime:1.7.1")

    implementation ("com.google.accompanist:accompanist-placeholder-material:0.13.0")


}

kapt {
    correctErrorTypes = true
}
