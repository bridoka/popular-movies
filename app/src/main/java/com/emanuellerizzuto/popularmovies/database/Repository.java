package com.emanuellerizzuto.popularmovies.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import com.emanuellerizzuto.popularmovies.utilities.AppExecutors;
import java.util.List;

public class Repository {

    private Context context;

    public Repository(Context context) {
        this.context = context;
    }

    public void insertFavoriteMovie(final MovieEntity movieEntity) {
        final AppDatabase db = AppDatabase.getInstance(this.context);
        AppExecutors executors = AppExecutors.getInstance();
        executors.diskIO().execute(new Runnable() {
                                       @Override
                                       public void run() {
                                            db.movieDao().insertMovie(movieEntity);
                                       }
                                   }

        );
    }

    public void removeFavoriteMovie(final MovieEntity movieEntity) {
        final AppDatabase db = AppDatabase.getInstance(this.context);
        AppExecutors executors = AppExecutors.getInstance();
        executors.diskIO().execute(new Runnable() {
                                       @Override
                                       public void run() {
                                           db.movieDao().deleteMovie(movieEntity);
                                       }
                                   }

        );
    }

    public LiveData<List<MovieEntity>> getAllFavoriteMovies() {
        final AppDatabase db = AppDatabase.getInstance(this.context);
        return db.movieDao().loadAllMovies();
    }

    public LiveData<MovieEntity> getMovieByTitle(String title) {
        final AppDatabase db = AppDatabase.getInstance(this.context);
        return db.movieDao().loadMovieByTitle(title);
    }
}
