package com.emanuellerizzuto.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager linearLayoutManager =
                new GridLayoutManager(this, 4);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        loadMoviesData();
    }

    public void loadMoviesData() {
        new LoadMovies().execute(1);
    }

    @Override
    public void onClick(MoviesResultsParcelable moviesResultsParcelable) {
        Log.d("teste","teste");
        Context context = this;
        Class destination = MovieDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, destination);
        intentToStartActivity.putExtra("title",moviesResultsParcelable.getTitle());
        intentToStartActivity.putExtra("overview",moviesResultsParcelable.getOverview());
        intentToStartActivity.putExtra("releaseDate", moviesResultsParcelable.getReleaseDate());
        intentToStartActivity.putExtra("posterPath", moviesResultsParcelable.getPosterPath());
        intentToStartActivity.putExtra("voteAverage", moviesResultsParcelable.getVoteAverage());
        startActivity(intentToStartActivity);
    }

    public class LoadMovies extends AsyncTask<Integer, Void, MoviesParcelable> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MoviesParcelable doInBackground(Integer... params) {
            if (params.length == 0) {
                return null;
            }
            return MoviesJsonUtils.getPopularMoviesFromJson(params[0]);
        }

        @Override
        protected void onPostExecute(MoviesParcelable moviesParcelable) {
            if (moviesParcelable != null) {
                moviesAdapter.setMoviesResults(moviesParcelable.getMoviesResults());
            }
        }
    }
}
