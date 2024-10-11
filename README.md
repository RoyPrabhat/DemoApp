# DemoApp

The Demo App contains two simple pages and an error screen. It in essence is a list of items and their details page. 
Items in our case are movies and the data is being fetched from free to use open source TheMovideDB platform.

**Feature**
1) Open the app to see top rated movies (home page).
2) Click on any one of the movies on the home page to see the movie details page.
3) Press back to come to the home page
4) In case of any API error, the error screen will be displayed with a retry button to make the API call again along with a toast message that lets the user know that something went wrong.
   
**Follwoing are the things that I have tried to cover as a part of this app**
1) MVVM architechture
2) Clean architecture
3) Flow
4) Coroutines
5) Compose
6) Unit testing
7) Lifecyle
8) Dependency Injection using Hilt + Dagger
9) API error response handling (For now only a toast is being displayed)
10) Securely storing API Key in BuildConfig
11) Minify & code shrinking
12) Retry button in case of api failure to call the api again
13) Screen loader
14) Placeholder images
15) Testable code
    
**Application Screenshots**

Following is how the UI looks like in Version 1 of the app

**1) Home Page**  





![Home_Screen](https://github.com/user-attachments/assets/484bde37-9ea8-4fd5-8c14-05919108bd80)





**2)  Movie Details Page** 





![Details_Screen](https://github.com/user-attachments/assets/9dc3cbaa-999d-4ecb-b3ed-29fa3c80a6e5)





**2)  Error Screen** 





![RetryScreen](https://github.com/user-attachments/assets/6a6a2bb8-2390-4b12-9f8c-76ed9d95bf53)
