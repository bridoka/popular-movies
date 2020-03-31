package com.emanuellerizzuto.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
        LoaderManager.LoaderCallbacks<MoviesParcelable>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;

    private ArrayList<MoviesResultsParcelable> moviesResultsParcelableList;

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    private static final int LOAD_ID = 0;

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

        if (savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            moviesResultsParcelableList = savedInstanceState.getParcelableArrayList("movies");
        }

        String orderType = MoviesPreferences.getSortOrder(this);

        int loaderId = LOAD_ID;

        LoaderManager.LoaderCallbacks<MoviesParcelable> callback = MainActivity.this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString("type", orderType);
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

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

            moviesAdapter.setMoviesResults(null);
            restartLoader();

            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    private void restartLoader() {
        int loaderId = LOAD_ID;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString("type", MoviesPreferences.getSortOrder(this));
        getSupportLoaderManager().restartLoader(loaderId, bundleForLoader, this);
    }

    @NonNull
    @Override
    public Loader<MoviesParcelable> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<MoviesParcelable>(this) {
            MoviesParcelable moviesParcelableData = null;
            @Nullable
            @Override
            public MoviesParcelable loadInBackground() {
                String orderType = args.getString("type");
                if (orderType == null) {
                    orderType = "top_rated";
                }
                return MoviesJsonUtils.getPopularMoviesFromJson(1, orderType);

            }

            @Override
            protected void onStartLoading() {
                if (moviesParcelableData != null) {
                    deliverResult(moviesParcelableData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable MoviesParcelable data) {
                moviesParcelableData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MoviesParcelable> loader, MoviesParcelable moviesParcelable) {
        if (moviesParcelable != null) {
            moviesResultsParcelableList = moviesParcelable.getMoviesResults();
        }
        moviesAdapter.setMoviesResults(moviesResultsParcelableList);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MoviesParcelable> loader) {

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("movies", moviesResultsParcelableList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
