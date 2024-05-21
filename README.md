# UNICOURSE - Mobile App cung cấp khóa học trực tuyến

## Các màn hình chính

### ONBOARDING SCREEN

<div style="display: flex; justify-content: center; align-items: center; margin: auto; width: 100%;">
  <img width="22%" src="./assets/image_readme/onboar1.png" style="background-color: black; padding: 10px; border-radius: 10px; margin: 10px;" />
  <img width="22%" src="./assets/image_readme/onboar2.png" style="background-color: black; padding: 10px; border-radius: 10px; margin: 10px;" />
  <img width="22%" src="./assets/image_readme/onboar3.png" style="background-color: black; padding: 10px; border-radius: 10px; margin: 10px;" />
  <img width="22%" src="./assets/image_readme/login_screen.png" style="background-color: black; padding: 10px; border-radius: 10px; margin: 10px;" />
</div>

## Project Structure
```plaintext

Unicourse/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── unicourse/
│   │   │   │           ├── adapters/
│   │   │   │           │   └── ControllerAdapter.java
│   │   │   │           ├── models/
│   │   │   │           │   ├── Course.java
│   │   │   │           │   ├── User.java
│   │   │   │           ├── services/
│   │   │   │           │   ├── ApiClient.java
│   │   │   │           │   ├── ApiInterface.java
│   │   │   │           ├── repositories/
│   │   │   │           │   └── ControllerRepository.java
│   │   │   │           ├── ui/
│   │   │   │           │   ├── activities/
│   │   │   │           │   │   ├── LoginActivity.java
│   │   │   │           │   │   ├── MainActivity.java
│   │   │   │           │   │   └── CourseDetailActivity.java
│   │   │   │           └── UnicourseApplication.java
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── styles.xml
│   │   │   │   └── AndroidManifest.xml
│   ├── build.gradle
├── gradle/
├── .gitignore
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md

```
