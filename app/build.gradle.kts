import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

android {
    namespace = "com.codeshinobi.zochitika"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codeshinobi.zochitika"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        var properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"${properties.getProperty("SUPABASE_ANON_KEY")}\"")
        buildConfigField("String", "SECRET", "\"${properties.getProperty("SECRET")}\"")
        buildConfigField("String", "SUPABASE_URL", "\"${properties.getProperty("SUPABASE_URL")}\"")

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

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation ("io.github.jan-tennert.supabase:postgrest-kt")
    implementation ("io.ktor:ktor-client-android")

    implementation( "org.jetbrains.kotlin:kotlin-serialization")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    implementation ("io.github.jan-tennert.supabase:postgrest-kt")
    implementation ("io.github.jan-tennert.supabase:storage-kt")
    implementation ("io.github.jan-tennert.supabase:gotrue-kt")
    implementation ("io.ktor:ktor-client-android")
    implementation ("io.ktor:ktor-client-core")
    implementation ("io.ktor:ktor-utils")

    implementation ("com.google.dagger:hilt-android")
    annotationProcessor ("com.google.dagger:hilt-compiler")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation("com.github.supabase:supabase-java:0.1.0")

    implementation(platform("io.github.jan-tennert.supabase:bom:VERSION"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")


}