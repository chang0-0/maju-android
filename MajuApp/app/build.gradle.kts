plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.app.majuapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.majuapp"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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

    // navigation
    dependencies {
        // val nav_version = "2.7.7"
        implementation("androidx.navigation:navigation-fragment-compose:2.8.0-alpha07")
    }

    // animation
    implementation("androidx.compose.ui:ui:1.7.0-alpha07")
    implementation("androidx.compose.animation:animation:1.7.0-alpha07")
    implementation("androidx.compose.foundation:foundation:1.7.0-alpha07")

    // material
    dependencies {
        implementation("androidx.compose.material3:material3:1.2.1")
        implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
        implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha05")
        implementation("androidx.compose.material:material-icons-extended:1.6.6")
    }

    // OkHttp3
    dependencies {
        // define a BOM and its version
        implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

        // define any required OkHttp artifacts without version
        implementation("com.squareup.okhttp3:okhttp")
        implementation("com.squareup.okhttp3:logging-interceptor")
    }

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.5.0")

    //GSON converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // LifeCycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1-Beta")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")

    // Paging 3.0
    dependencies {
        val paging_version = "3.2.1"

        implementation("androidx.paging:paging-runtime:$paging_version")

        // alternatively - without Android dependencies for tests
        testImplementation("androidx.paging:paging-common:$paging_version")

        // optional - RxJava2 support
        implementation("androidx.paging:paging-rxjava2:$paging_version")

        // optional - RxJava3 support
        implementation("androidx.paging:paging-rxjava3:$paging_version")

        // optional - Guava ListenableFuture support
        implementation("androidx.paging:paging-guava:$paging_version")

        // optional - Jetpack Compose integration
        implementation("androidx.paging:paging-compose:3.3.0-beta01")
    }

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    // Google Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.1")

    //Grid
    implementation("com.cheonjaeung.compose.grid:grid:2.0.0")

    // Google Map
    implementation("com.google.maps.android:maps-compose:5.0.3")

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation("com.google.maps.android:maps-compose-utils:5.0.3")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation("com.google.maps.android:maps-compose-widgets:5.0.3")

    // GoogleMap
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.maps.android:maps-compose-utils:4.3.3")
    implementation("com.google.maps.android:maps-compose-widgets:4.3.3")
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}

kapt {
    correctErrorTypes = true
}

