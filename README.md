gradle-jacoco-android
===============

![SonarQube coverage report][1]

generate jacoco coverage from androidTest

## Usage

### 1. Have a working Gradle build
This is up to you.

### 2. Put JacocoRunner.java to src/androidTest/com/alanjeon/testing/ folder

### 3. Set instrumentTestRunner to build.gradle

    android {
        compileSdkVersion 19
        buildToolsVersion "19.0.1"

        defaultConfig {
            minSdkVersion 8
            targetSdkVersion 19
            versionCode 1
            versionName "1.0"
            testInstrumentationRunner "com.alanjeon.testing.JacocoRunner"
        }

### 4. Add the following to the dependencies section

    androidTestCompile 'org.jacoco:org.jacoco.agent:0.6.4.201312101107:runtime'
    androidTestCompile 'com.jakewharton.espresso:espresso:1.1-r1'

### 5. Add jacoco-gradle dependencies

    configurations {
        jacocoAnt
    }

    dependencies {
        jacocoAnt 'org.jacoco:org.jacoco.ant:0.6.4.201312101107'
    }

### 6. Add the following to the end of build.gradle

    apply from: 'https://raw.github.com/skyisle/gradle-jacoco-android/master/gradle-jacoco-android.gradle'

### 7. Run jacoco coverage analysis script
It runs with connectedInstrumentTestDebug.
You can now run jacoco coverage with follwing command. If you are using flavors, you need to check ./gradlew tasks first.

    ./gradlew jacocoDebug

You can find jacoco.exec at build/jacoco/debug folder.

## License

    Copyright 2014 Alan Jeon

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]:https://raw.github.com/skyisle/gradle-jacoco-android/master/screenshot.png
