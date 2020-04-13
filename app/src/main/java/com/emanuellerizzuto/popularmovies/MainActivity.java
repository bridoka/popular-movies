package com.emanuellerizzuto.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesPreferences;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.MoviesAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;

    private ArrayList<MoviesResultsParcelable> moviesResultsParcelableList;

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    private static final int LOAD_ID = 0;

    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        this.viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        this.viewModel.getMoviesResultsParcelableList().observe(this, new Observer<ArrayList<MoviesResultsParcelable>>() {
            @Override
            public void onChanged(ArrayList<MoviesResultsParcelable> moviesResultsParcelables) {
                moviesAdapter.setMoviesResults(moviesResultsParcelables);
            }
        });
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
        intentToStartActivity.putExtra("backdropPath", moviesResultsParcelable.getBackdropPath());
        intentToStartActivity.putExtra("voteAverage", String.valueOf(moviesResultsParcelable.getVoteAverage()));
        startActivity(intentToStartActivity);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            restartLoader();
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    private void restartLoader() {
        moviesAdapter.setMoviesResults(null);
        this.viewModel.executeTask();
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

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
