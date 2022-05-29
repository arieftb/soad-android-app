
SOAD - Story of a Dicoding Android App
=====  

Just very simple project to share story of dicoding users. I made this project for graduate from  Dicoding online course about "**Belajar Pengembangan Aplikasi Android Intermediate**"


Branches
-------------  
* **main**  
  For latest update of the project


Feature
-------------  
* Login & Register
* Stories List
* Story Detail
* Post Story
* App Language Setting
* Widget


Tech Stack
----------  
* [AndroidX](https://developer.android.com/jetpack/androidx)
* [Kotlin](https://kotlinlang.org/)
* [Coroutine](https://github.com/Kotlin/kotlinx.coroutines)
* [Retrofit](https://github.com/square/retrofit)
* [Coil](https://github.com/coil-kt/coil)
* [Dagger](https://dagger.dev/hilt/)
* [Timber](https://github.com/JakeWharton/timber)


Architecture
-----------  
MVVM + Clean Architecture (From my learning perspective)

How to use it?
------  
- Clone this project using `git clone [url]`
- build the project by using `./gradlew clean assembleDebug`
- install the project to your device `./gradlew clean installDebug`
- Don't forget add env `API_URL` variable inside `local.properties` file with value from Dicoding Story API URL

*Clean Architecture will not be appropriate for every project, so it is down to you to decide whether or not it fits your needs*
