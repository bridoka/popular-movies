package com.emanuellerizzuto.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> loadAllMovies();

    @Insert
    void insertMovie(MovieEntity movieEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntity movieEntity);

    @Delete
    void deleteMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM movie WHERE title like :title")
    LiveData<MovieEntity> loadMovieByTitle(String title);
}
