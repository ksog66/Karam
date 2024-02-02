
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kspDevTool)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
}

android {
    namespace = "com.kklabs.karam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kklabs.karam"
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

            buildConfigField("String", "BASE_URL", "\"http://192.168.1.10:5000\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.10:5000\"")
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.retrofit.converter.moshi)

    //hilt
    implementation(libs.dagger.hiltAndroid)
    //datastore
    implementation(libs.androidx.datastore)

    //firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.playServicesAuth)

    implementation(libs.materialIcons.extended)
    implementation(libs.materialIcons.core)
    implementation(libs.hilt.navigationCompose)

    //lifecycle
    implementation(libs.lifecycle.viewModelCompose)
    implementation(libs.lifecycle.runtimeCompose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintCompose)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)

    //network logger
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    ksp(libs.androidx.room.compiler)
    ksp(libs.dagger.hiltCompiler)
    ksp(libs.moshi.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}