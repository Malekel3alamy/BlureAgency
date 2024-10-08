import java.util.regex.Pattern.compile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.blureagency"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.blureagency"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
//    androidResources {
//        generateLocaleConfig = true
//    }
    buildFeatures{
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes += "META-INF/LICENSE*"
        resources.excludes += "META-INF/NOTICE*"
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // LottieFiles Animation
    implementation ("com.airbnb.android:lottie:6.5.2")


    // Navigation Components
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")

    // Dots Indicator
    implementation("com.tbuonomo:dotsindicator:5.1.0")

    // Circular Image
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")

   // implementation("io.github.nurujjamanpollob.androidmailer:androidmailer:2.2.3")

    // Dagger hilt
    implementation("com.google.dagger:hilt-android:2.52")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    kapt("com.google.dagger:hilt-android-compiler:2.52")

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")
    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.6.1")

    implementation ("com.google.android.material:material:1.12.0")

// phone number picker
    implementation("com.hbb20:ccp:2.7.3")

    // sign in using google
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    // sign in with facebook
    implementation("com.facebook.android:facebook-login:latest.release")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}