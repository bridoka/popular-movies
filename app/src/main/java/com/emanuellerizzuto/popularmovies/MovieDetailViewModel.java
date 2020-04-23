package com.emanuellerizzuto.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.emanuellerizzuto.popularmovies.data.MovieReviewsParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieReviewsResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieVideosParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieVideosResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesPreferences;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.database.AppDatabase;
import com.emanuellerizzuto.popularmovies.database.MovieEntity;
import com.emanuellerizzuto.popularmovies.database.Repository;
import com.emanuellerizzuto.popularmovies.utilities.MovieVideosUtils;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;
import com.emanuellerizzuto.popularmovies.utilities.MoviesReviewsUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    private Context context;

    private Repository repository;

    private final String title;

    private final int idMovie;

    private LiveData<MovieEntity> movieEntity;

    private MutableLiveData<ArrayList<MovieReviewsResultsParcelable>> movieReviewsResultParcelableList = new MutableLiveData<>();

    private MutableLiveData<ArrayList<MovieVideosResultsParcelable>> movieVideosResultParcelableList = new MutableLiveData<>();

    public MovieDetailViewModel(Context context, String title, int idMovie) {
        this.context = context;
        this.title = title;
        this.idMovie = idMovie;
        getMovieByTitle();
        executeTask();
    }

    private Repository getRepository() {
        if (this.repository == null) {
            this.repository = new Repository(this.context);
        }
        return this.repository;
    }

    public void saveFavoriteMovie(MovieEntity movieEntity) {
        this.getRepository().insertFavoriteMovie(movieEntity);
    }

    public void removeFavoriteMovie(MovieEntity movieEntity) {
        this.getRepository().removeFavoriteMovie(movieEntity);
    }

    private void getMovieByTitle() {
        this.movieEntity = this.getRepository().getMovieByTitle(this.title);
    }

    public LiveData<MovieEntity> getMovieEntity() {
        return this.movieEntity;
    }

    public MutableLiveData<ArrayList<MovieReviewsResultsParcelable>> getMovieReviewsResultsParcelableList() {
        return movieReviewsResultParcelableList;
    }

    public MutableLiveData<ArrayList<MovieVideosResultsParcelable>> getMovieVideosResultsParcelableList() {
        return movieVideosResultParcelableList;
    }

    public void executeTask() {
        AddAsyncTask asyncTask = new AddAsyncTask();
        asyncTask.execute(this.idMovie);

        AddAsyncLoadVideosTask asyncLoadVideosTask = new AddAsyncLoadVideosTask();
        asyncLoadVideosTask.execute(this.idMovie);
    }

    private class AddAsyncTask extends AsyncTask<Integer, Void, MovieReviewsParcelable> {
        @Override
        protected MovieReviewsParcelable doInBackground(Integer ...params) {
            return MoviesReviewsUtils.getMovieReviewsFromJson(params[0]);
        }

        @Override
        protected void onPostExecute(MovieReviewsParcelable movieReviewsParcelable) {
            if (movieReviewsParcelable != null) {
                movieReviewsResultParcelableList.setValue(movieReviewsParcelable.getMoviesReviewsResults());
            }
        }
    }

    private class AddAsyncLoadVideosTask extends AsyncTask<Integer, Void, MovieVideosParcelable> {
        @Override
        protected MovieVideosParcelable doInBackground(Integer ...params) {
            return MovieVideosUtils.getMovieVideosFromJson(params[0]);
        }

        @Override
        protected void onPostExecute(MovieVideosParcelable movieVideosParcelable) {
            if (movieVideosParcelable != null) {
                movieVideosResultParcelableList.setValue(movieVideosParcelable.getMovieVideosResults());
            }
        }
    }
}
