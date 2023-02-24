# The Movie List
The movie list was the final project of the Vitória Code Bootcamp, sponsored by PicPay, and aimed to integrate the knowledge acquired during the course.

The project aimed to implement a movie list using the TMDB API. The requirements were:

- The application must be faithful to the UI;
- The project must be modularized, following the Clean and MVVM architecture standards;
- The application must store favorite movies locally;
- Use Crashlytics to map any usage failures;
- Implement automated tests for the created features

Of these points, I could implement only three: implementation according to the UI, modularized design following MVVM architecture, and local storage of movies. Tests are in progress.

## Screens

- Home

  The app's home screen consists of two categories: all movies listed and the user's favorite movies.

  ![ezgif com-gif-maker](https://user-images.githubusercontent.com/77559052/214552457-25b187f8-dac1-445d-ada2-a93b21fc8028.png) ![ezgif com-gif-maker-2](https://user-images.githubusercontent.com/77559052/214553129-5c5bbc5c-c5ec-471e-ab7c-6c670bb21717.png)

 
- Filter
 
   When you tap a category, movies are filtered. It is crucial to mention that the user can also apply the filter in search mode.
   
   ![ezgif com-gif-maker-3](https://user-images.githubusercontent.com/77559052/214553989-00375beb-ecb3-446d-911e-013eb16a39a3.png)
   
 
 - Movie details
 
   When clicking on a movie, the user navigates to a screen containing favorite information, the movie's synopsis, evaluation, and its cast.
   
   ![ezgif com-gif-maker-4](https://user-images.githubusercontent.com/77559052/214554555-eadb108e-a0ce-44e7-9baa-4eb868d3cacc.png)
   
   
  - Search mode
  
    When you start typing in the search field, a new screen shows the search results. The user can go back to the previous state by clearing the text field or using the link below the title.

    ![ezgif com-gif-maker-5](https://user-images.githubusercontent.com/77559052/214563143-c202449e-0f89-40fa-88a5-0b1bfd5e3740.png)
    
  
  - Errors
  
    The mapped scenarios are generic errors for request failures and movies not found in a search.
    
    ![ezgif com-gif-maker-6](https://user-images.githubusercontent.com/77559052/214564371-2b284b39-22d7-4d06-8bd8-c9c934a814c7.png) ![ezgif com-gif-maker-7](https://user-images.githubusercontent.com/77559052/214564567-12b3109f-17b5-4f3a-9eee-823a13b83b1e.png)


## Android technologies

  On this project I tried to implement:
  
    - RX Kotlin on the search mode;
    - Koin to manage dependency injection;
    - Room to store favorite movies locally;
    - Navigation component to navigate between screens;
    
    
## Improvements

  - Room is working, but the state of the favorite button is not working as it is supposed to. Sometimes it is enabled, and sometimes it is not.
  - Crashlytics is not implemented;
  


   
