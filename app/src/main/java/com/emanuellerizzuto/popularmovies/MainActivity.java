package com.emanuellerizzuto.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;

    private ArrayList<MoviesResultsParcelable> moviesResultsParcelableList;

    private static final String mostPopular = "most_popular";

    private static final String topRated = "top_rated";

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

        if (savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            moviesResultsParcelableList = savedInstanceState.getParcelableArrayList("movies");
        }

        loadMoviesData(mostPopular);
    }

    public void loadMoviesData(String type) {
        new LoadMovies().execute(type);
    }

    @Override
    public void onClick(MoviesResultsParcelable moviesResultsParcelable) {
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

    public class LoadMovies extends AsyncTask<String, Void, MoviesParcelable> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MoviesParcelable doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            return MoviesJsonUtils.getPopularMoviesFromJson(1, params[0]);
        }

        @Override
        protected void onPostExecute(MoviesParcelable moviesParcelable) {
            if (moviesParcelable != null && moviesResultsParcelableList == null) {
                moviesResultsParcelableList = moviesParcelable.getMoviesResults();
            }
            moviesAdapter.setMoviesResults(moviesResultsParcelableList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.most_popular) {
            moviesResultsParcelableList = null;
            moviesAdapter.setMoviesResults(null);
            loadMoviesData(mostPopular);
            return true;
        }

        if (id == R.id.top_rated) {
            moviesResultsParcelableList = null;
            moviesAdapter.setMoviesResults(null);
            loadMoviesData(topRated);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("movies", moviesResultsParcelableList);
        super.onSaveInstanceState(outState);
    }
}
