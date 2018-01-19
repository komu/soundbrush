plugins {
    kotlin("jvm") version "1.2.20"
}

repositories {
    jcenter()
}

val kotlinVersion = "1.2.20"

dependencies {
    implementation(kotlin("stdlib-jre8", kotlinVersion))
    
    testImplementation("junit:junit:4.12")
    testImplementation(kotlin("test", kotlinVersion))
}
