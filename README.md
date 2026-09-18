# Smart Campus App

An Android Studio project that brings common campus services into one small Java application. Smart Campus App combines student login, health support, food ordering, appointments, intent demonstrations, quizzes, and student registration in a single dashboard.

> Built for RBU B.Tech CSE Semester IV, Software Laboratory-II (24CS01PR0404).

## Features

| Module | What it demonstrates | Main activity |
| --- | --- | --- |
| Activity lifecycle | Lifecycle callbacks, logging, and themed UI | `SplashActivity` |
| Health checkup | Nested `LinearLayout`, `RelativeLayout`, and `ConstraintLayout` | `HealthCheckupActivity` |
| Food ordering | Form events, `TextWatcher`, and checkbox validation | `FoodOrderingActivity` |
| Hospital appointment | Constraint-based form validation and date/time selection | `AppointmentActivity` |
| Intent actions | Explicit navigation plus dial, email, and share intents | `IntentActionActivity` |
| Quiz | Dialog confirmation, score calculation, and notifications | `QuizActivity` |
| Persistent login | `SharedPreferences` login state | `LoginActivity` |
| Student registration | SQLite insert and read operations | `StudentRegistrationActivity` |

## App flow

```text
Splash screen
    |
    +-- First launch --> Login --> Dashboard
    |
    +-- Returning launch --------------^
                                      |
        Health | Food | Appointment | Quiz | Student registration
                                      |
                              Intent actions
```

## Requirements

- Android Studio Hedgehog or newer
- Android SDK 34
- Java 8
- Android device or emulator running API 24 or newer

## Run the project

1. Open the `SmartCampusApp` folder in Android Studio.
2. Allow Gradle to sync and download dependencies on the first run.
3. Connect an Android device with USB debugging enabled, or start an API 24+ emulator.
4. Select the `app` configuration and click **Run**.

The Gradle wrapper is included, so command-line builds can also be run with:

```bash
./gradlew assembleDebug
```

The generated APK is written to `app/build/outputs/apk/debug/`.

## Project structure

```text
SmartCampusApp/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/smartcampus/
│       │   ├── activities/
│       │   └── database/DatabaseHelper.java
│       └── res/
│           ├── layout/
│           ├── values/
│           └── drawable/
├── build.gradle
├── gradle.properties
├── gradlew
└── settings.gradle
```

## Technical details

- Namespace and application ID: `com.smartcampus`
- Minimum SDK: 24
- Target and compile SDK: 34
- Dependencies: AndroidX AppCompat, Material Components, ConstraintLayout, and CardView
- Storage: `SharedPreferences` for login state and SQLite for student records

## Learning coverage

This project is organized as eight practical exercises while still behaving like one coherent campus utility app. It is intended for learning Android activities, layouts, validation, intents, notifications, preferences, and local database operations.
