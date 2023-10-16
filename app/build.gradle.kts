plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
val waifuBaseUrl: String =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("WAIFU_BASE_URL")
val pokemonBaseUrl: String =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("POKEMON_BASE_URL")
val navVersion = "2.7.2" /* Nav Compose */

android {
    namespace = "id.dayona.pokemonx"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.dayona.pokemonx"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "WAIFU_BASE_URL", waifuBaseUrl)
            buildConfigField("String", "POKEMON_BASE_URL", pokemonBaseUrl)
        }
        release {
            isCrunchPngs = true
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "WAIFU_BASE_URL", waifuBaseUrl)
            buildConfigField("String", "POKEMON_BASE_URL", pokemonBaseUrl)

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha") /* permission hanlder */
    implementation("androidx.activity:activity-compose:1.8.0") /* compose and material */
    implementation(platform("androidx.compose:compose-bom:2023.03.00")) /* compose and material */
    implementation("androidx.compose.ui:ui") /* compose and material */
    implementation("androidx.compose.ui:ui-graphics") /* compose and material */
    implementation("androidx.compose.ui:ui-tooling-preview") /* compose and material */
    implementation("androidx.compose.material3:material3") /* compose and material */
    implementation("androidx.compose.material:material") /* compose and material */
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00")) /* compose and material */
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") /* compose and material */
    debugImplementation("androidx.compose.ui:ui-tooling") /* compose and material */
    debugImplementation("androidx.compose.ui:ui-test-manifest") /* compose and material */
    implementation("com.google.dagger:hilt-android:2.48")    /* dagger hilt */
    implementation("androidx.hilt:hilt-work:1.0.0") /* dagger hilt */
    kapt("com.google.dagger:hilt-android-compiler:2.48") /* dagger hilt */
    implementation("androidx.work:work-runtime-ktx:2.8.1") /* worker */
    implementation("androidx.navigation:navigation-compose:$navVersion") /* Nav Compose */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")  /* Network */
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  /* Network */
    implementation("com.google.code.gson:gson:2.10.1")  /* GSON */
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))  /* Network */
    implementation("com.squareup.okhttp3:okhttp")  /* Network */
    implementation("com.squareup.okhttp3:logging-interceptor")  /* Network */
    implementation("io.coil-kt:coil-compose:2.4.0") /* COIL COMPOSE*/
    implementation("io.coil-kt:coil-gif:2.4.0") /* COIL GIF*/
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") /* Live Data */
}