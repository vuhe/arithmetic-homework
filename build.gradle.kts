import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    application
}

group = "top.vuhe"
version = "3.0"

application { mainClass.set("top.vuhe.MainKt") }

repositories {
    // maven 本地缓存仓库
    // 默认优先从此仓库查找
    mavenLocal()
    // maven 中央库
    mavenCentral()
}

dependencies {
    // 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.1-native-mt")

    // 日志
    implementation("ch.qos.logback:logback-classic:1.2.11")

    // 序列化
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    // 测试
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test { useJUnitPlatform() }

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
