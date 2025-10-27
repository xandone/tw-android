plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.xandone.twandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.xandone.twandroid"
        minSdk = 24
        targetSdk = 35
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

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //文字识别
    implementation("com.google.mlkit:digital-ink-recognition:18.0.0")

    // Room
    implementation("androidx.room:room-runtime:2.7.1")
    kapt("androidx.room:room-compiler:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
    implementation("androidx.room:room-paging:2.7.1")

    //工具
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.blankj:utilcodex:1.31.1")

    //ui
    implementation("com.github.hackware1993:MagicIndicator:1.7.0")
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.2")
    implementation("com.geyifeng.immersionbar:immersionbar:3.2.2")//沉浸式
    implementation("com.geyifeng.immersionbar:immersionbar-ktx:3.2.2")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")


}