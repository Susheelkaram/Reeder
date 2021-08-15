# Reeder - RSS Reader for Android
![Reeder app](https://user-images.githubusercontent.com/16625975/129471771-ccbcb0e0-5785-46dd-b18d-7c5d3a5a8b46.png)
Reeder is a beautifully built RSS feed reader for Android built using Kotlin. The app lets users add multiple RSS feeds and read them at one place.

[![Download Reeder apk](https://img.shields.io/badge/Download%20APK-v1.0-brightgreen "Download Reeder apk")](https://github.com/Susheelkaram/Reeder/releases/download/v1.0/reeder.apk "Download Reeder apk")
## Features
- **Read feeds** - You can add multiple feeds and read them all at one place.
- **Push notifications** -  The app scans the feeds in background and notifies user of new articles through push notifications.
- **Offline mode** -  The app caches the articles to local db so you can read them without internet.

# Screenshots
| Light mode        | Dark mode          | 
| ------------- |:-------------:|
|     ![](https://raw.githubusercontent.com/Susheelkaram/Reeder/master/screenshots/reeder_1.png)      |    ![](https://raw.githubusercontent.com/Susheelkaram/Reeder/master/screenshots/reeder_dark_1.png) | 
|  ![](https://raw.githubusercontent.com/Susheelkaram/Reeder/master/screenshots/reeder_2.png)   |   ![](https://raw.githubusercontent.com/Susheelkaram/Reeder/master/screenshots/reeder_dark_2.png )| 

# Built using
1. **Language -** Kotlin
2. **Architechture -** MVVM
3. **Design tool** - Adobe XD
4. [**RSS Parser**](https://github.com/prof18/RSS-Parser "**RSS Parser**") - For fetching and parsing RSS XML feeds
5. **Room DB** - For Offline caching
6. **ViewPager 2**  - For switching fragments with BottomNavigation
7. **Glide** - Image parsing library
8. **Work Manager ** - Jetpack library for deferrable background tasks
9. **ViewModel, Databinding, Moshi, RecyclerView**
