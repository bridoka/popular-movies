# Popular Movies APP
Project: Udacity - Android Nanodegree - Popular Movies Project
#### Project Summary
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, I build an app to allow users to discover the most popular movies playing.

#### Project configuration
This project has integration with the themoviedb.org.
In order to execute the project, it will be necessary to include the API-KEY generated in themoviesdb.org, in the project configuration file, which contains the variable with the same name.

app/src/main/java/com/emanuellerizzuto/popularmovies/config/Config.java

#### This app will:
* Upon launch, present the user with an grid arrangement of movie posters.
* Allow your user to change sort order via a setting:
  * The sort order can be by most popular, or by top rated
* Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
    * original title
    * movie poster image thumbnail
    * A plot synopsis (called overview in the api)
    * user rating (called vote_average in the api)
    * release date
