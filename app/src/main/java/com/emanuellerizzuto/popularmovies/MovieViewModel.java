package com.emanuellerizzuto.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesPreferences;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.database.MovieEntity;
import com.emanuellerizzuto.popularmovies.database.Repository;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private static final String mostPopular = "most_popular";
    private static final String top_rated = "top_rated";
    private static final String favorite = "favorite";
    private final Repository repository;
    Context context;
    private boolean inicialized = false;

    private MutableLiveData<ArrayList<MoviesResultsParcelable>> moviesResultsParcelableList = new MutableLiveData<>();

    public MovieViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getBaseContext();
        this.repository = new Repository(this.context);
        executeTask();
    }

    public MutableLiveData<ArrayList<MoviesResultsParcelable>> getMoviesResultsParcelableList() {
        return moviesResultsParcelableList;
    }

    public void executeTask() {
        final MutableLiveData<List<MovieEntity>> favoriteMoviesData = null;
        this.repository.getAllFavoriteMovies().observeForever(new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> favoriteMovies) {
                AddAsyncTask asyncTask = new AddAsyncTask();
                asyncTask.execute(favoriteMovies);
            }
        });
    }

    private class AddAsyncTask extends AsyncTask<List<MovieEntity>, Void, MoviesParcelable> {
        @Override
        protected MoviesParcelable doInBackground(List<MovieEntity>... movies) {
            String orderType = MoviesPreferences.getSortOrder(context);
            if (orderType.equals(top_rated) || orderType.equals(mostPopular)) {
                return MoviesJsonUtils.getMoviesFromJson(1, orderType);
            }

            return MoviesJsonUtils.getMoviesFromList(movies[0]);
        }

        @Override
        protected void onPostExecute(MoviesParcelable moviesParcelable) {
            String orderType = MoviesPreferences.getSortOrder(context);
            if (moviesParcelable == null && !orderType.equals(favorite)) {
                return;
            }
            moviesResultsParcelableList.setValue(moviesParcelable.getMoviesResults());
        }
    }
}
