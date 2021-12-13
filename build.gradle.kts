/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    java
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.2")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.slf4j:slf4j-jdk14:1.7.32")
    implementation("org.mongodb:mongo-java-driver:3.12.10")
}

group = "me.kannacoco"
version = "1.0"
description = "KannaBotto"
java.sourceCompatibility = JavaVersion.VERSION_11
