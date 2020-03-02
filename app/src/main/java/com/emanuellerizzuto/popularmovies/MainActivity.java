package com.emanuellerizzuto.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMoviesData();
    }

    public void loadMoviesData() {
        new LoadMovies().execute(1);
    }

    public class LoadMovies extends AsyncTask<Integer, Void, MoviesParcelable> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MoviesParcelable doInBackground(Integer... params) {
            MoviesParcelable movies = MoviesJsonUtils.getPopularMoviesFromJson(params[0]);
            return movies;
        }

        @Override
        protected void onPostExecute(MoviesParcelable moviesParcelable) {
            super.onPostExecute(moviesParcelable);
        }
    }
}
