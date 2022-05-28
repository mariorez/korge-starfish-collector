import com.soywiz.korge.gradle.KorgeGradlePlugin
import com.soywiz.korge.gradle.korge

buildscript {
    val korgePluginVersion: String by project

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
    }
}

apply<KorgeGradlePlugin>()

korge {
    id = "mariorez.starfishcollector"
    name = "Starfish Collector"

    supportFleks()

    targetJvm()
    targetJs()
    targetDesktop()
    targetIos()
    targetAndroidIndirect() // targetAndroidDirect()
}