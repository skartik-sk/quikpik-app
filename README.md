# QuikPic: Seamless Social Media on Android

## Introduction

Welcome to **QuikPic**, a dynamic and intuitive social media application built natively for Android. Designed to provide a seamless and engaging user experience, QuikPic allows users to connect with friends, share moments through photos, and interact effortlessly through likes, comments, and shares.

## Features

* **User Authentication:** Secure login, signup, and logout functionalities.
* **Post Functionality:** Share photos, comment on posts, like, save, and explore public content.
* **Social Interactions:** Follow/unfollow users and view follower/following lists.
* **Real-time Updates:** Dynamic feeds and instant notifications for new interactions.

## Technologies Used

* **Kotlin:** Primary programming language for Android development.
* **Jetpack Compose:** Modern toolkit for building native Android UI declaratively.
* **MVVM Architecture:** Clean, testable, and maintainable code structure.
* **Dagger Hilt:** Dependency Injection framework for robust and scalable app development.
* **Retrofit:** Type-safe HTTP client for consuming RESTful APIs.
* **Material Design:** Modern UI components and guidelines for a consistent look and feel.

## Architecture (MVVM)

QuikPic is built following the **Model-View-ViewModel (MVVM)** architectural pattern. This separation of concerns ensures:

* **Modularity:** Clear distinction between UI, business logic, and data layers.
* **Testability:** Easier to write unit tests for ViewModel and Model components.
* **Maintainability:** Simplified code management and future feature additions.

Data flow is managed efficiently, with **Retrofit** handling network requests to the backend API, and **Dagger Hilt** providing robust dependency injection across the application for a scalable and testable codebase.

## Setup and Installation

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Android Studio Arctic Fox or higher
* Kotlin 1.5+
* JDK 11+

### Installation

1.  Clone the repository:
    ```
    git clone https://github.com/skartik-sk/QuikPic.git
    ```
2.  Open the project in Android Studio.
3.  Sync Gradle files.
4.  Run on an emulator or a physical device.

## APK Download

You can download the latest APK directly from here:
[Download QuikPic APK](https://github.com/skartik-sk/quikpik-app/releases/download/V1/app-release.apk)
*(Please replace YOUR_APK_DOWNLOAD_LINK_HERE with the actual link to your APK file, e.g., from a GitHub Release, Google Drive, or other hosting service.)*

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.
