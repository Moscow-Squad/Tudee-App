plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.gms.google-services")
    id("jacoco")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.moscow.tudee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.moscow.tudee"
        minSdk = 30
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
        debug {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.koin)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    ksp(libs.androidx.room.compiler)

    // Test dependencies
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.room.testing)

    // Android test dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/**
 * git hooks installation task
 * */
tasks.register("installGitHooks") {
    doLast {
        fun installHook(name: String) {
            val source = rootProject.file("scripts/hooks/$name")
            val target = rootProject.file(".git/hooks/$name")

            if (!target.exists() || target.readText() != source.readText()) {
                target.writeText(source.readText())
                target.setExecutable(true)
                source.setReadable(true)
                source.setWritable(false)
                println("✅ $name hook installed and made executable.")
            } else {
                println("✅ $name hook is already up to date.")
            }
        }

        installHook("commit-msg")
        installHook("pre-push")
    }
}

gradle.projectsEvaluated {
    tasks["build"].dependsOn("installGitHooks")
}


/**
 * jacoco configuration
 * */
jacoco {
    toolVersion = "0.8.11"
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        xml.outputLocation.set(file("${buildDir}/reports/jacoco/test/jacocoTestReport.xml"))
        html.outputLocation.set(file("${buildDir}/reports/jacoco/test/html"))
    }

    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    val mainSrc = "${project.projectDir}/src/main/java"
    val kotlinSrc = "${project.projectDir}/src/main/kotlin"

    sourceDirectories.setFrom(files(listOf(mainSrc, kotlinSrc)))
    classDirectories.setFrom(files(listOf(debugTree)))
    executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn("testDebugUnitTest")

    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    classDirectories.setFrom(files(listOf(debugTree)))
    executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))

    violationRules {
        rule {
            limit {
                minimum = 0.70.toBigDecimal()
            }
        }
    }
}