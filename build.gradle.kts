import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
buildscript{
    repositories{
        maven{
            setUrl("https://plugins.gradle.org/m2/")
            setUrl("https://jitpack.io")
        }
    }
    dependencies{
        classpath("org.openjfx:javafx-plugin:0.0.8")
        classpath("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
    }
}
plugins {
    kotlin("jvm") version "1.3.72"
    id("org.openjfx.javafxplugin") version "0.0.8"
    application
}
javafx{
    modules("javafx.controls","javafx.fxml")
}
application{
    mainClassName="cyxTankWar.AppKt"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}
repositories {
    mavenCentral()
    maven{setUrl("https://jitpack.io")}
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}