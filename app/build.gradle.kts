plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.unicourse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.unicourse"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src\\main\\java", "src\\main\\java\\adapters")
            }
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    implementation(libs.swiperefreshlayout)
    implementation(fileTree(mapOf("dir" to "C:\\ZaloPayLibs", "include" to listOf("*.aar", "*.jar"), "exclude" to listOf(""))))
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.appcompat)
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.auth0:java-jwt:3.18.1")
    implementation("io.socket:socket.io-client:2.0.1")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.airbnb.android:lottie:3.7.2")
    configurations.maybeCreate("default")
    artifacts.add("default", file("zpdk-release.aar"))
    implementation ("com.paypal.sdk:paypal-android-sdk:2.16.0")
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.animation.core.android)
    testImplementation(libs.junit)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}