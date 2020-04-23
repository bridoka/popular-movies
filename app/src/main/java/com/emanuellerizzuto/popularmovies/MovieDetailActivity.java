package com.emanuellerizzuto.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emanuellerizzuto.popularmovies.config.Config;
import com.emanuellerizzuto.popularmovies.data.MovieReviewsResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieVideosResultsParcelable;
import com.emanuellerizzuto.popularmovies.database.MovieEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity
        implements MovieVideosAdapter.MovieVideosAdapterOnClickHandler {

    private MovieDetailViewModel viewModel;
    private RecyclerView reviewsRecyclerView;
    private RecyclerView videosRecyclerView;
    private MovieReviewsAdapter movieReviewsAdapter;
    private MovieVideosAdapter movieVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvOverview = findViewById(R.id.tv_overview);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        TextView tvVoteAverage = findViewById(R.id.tv_vote_average);
        ImageView ivPath = findViewById(R.id.iv_poster_path);
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            tvTitle.setText(intent.getStringExtra("title"));
            tvOverview.setText(intent.getStringExtra("overview"));
            tvReleaseDate.setText(intent.getStringExtra("releaseDate").split("-")[0]);
            tvVoteAverage.setText(String.valueOf(intent.getDoubleExtra("voteAverage", 0)) + "/10");
            Picasso.get()
                    .load(Config.MOVIES_IMAGES_URL+intent.getStringExtra("posterPath"))
                    .into(ivPath);
        }

        final Button favoriteButton = findViewById(R.id.button_favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveFavoriteButtonClicked();
            }
        });

        MovieDetailViewModelFactory movieDetailViewModelFactory =
                new MovieDetailViewModelFactory(getBaseContext(),
                        intent.getStringExtra("title"),
                        intent.getIntExtra("id", 0));

        this.viewModel = ViewModelProviders.of(this, movieDetailViewModelFactory).get(MovieDetailViewModel.class);


        this.viewModel.getMovieEntity().observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(final MovieEntity movieEntity) {
                if (movieEntity != null) {
                    favoriteButton.setVisibility(View.GONE);

                    final Button removeFavoriteButton = findViewById(R.id.button_rm_favorite);
                    removeFavoriteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onRemoveFavoriteButtonClicked(movieEntity);
                        }
                    });

                    removeFavoriteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        setupRecyclerView();
        setupRecyclerViewVideos();
    }

    private void setupRecyclerView() {
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        movieReviewsAdapter = new MovieReviewsAdapter();
        reviewsRecyclerView.setAdapter(movieReviewsAdapter);

        this.viewModel.getMovieReviewsResultsParcelableList().observe(this, new Observer<ArrayList<MovieReviewsResultsParcelable>>() {
            @Override
            public void onChanged(ArrayList<MovieReviewsResultsParcelable> movieReviewsResultsParcelables) {
                movieReviewsAdapter.setMovieReviews(movieReviewsResultsParcelables);
            }
        });
    }

    private void setupRecyclerViewVideos() {
        videosRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        videosRecyclerView.setLayoutManager(layoutManager);
        videosRecyclerView.setHasFixedSize(true);
        movieVideosAdapter = new MovieVideosAdapter(this);
        videosRecyclerView.setAdapter(movieVideosAdapter);

        this.viewModel.getMovieVideosResultsParcelableList().observe(this, new Observer<ArrayList<MovieVideosResultsParcelable>>() {
            @Override
            public void onChanged(ArrayList<MovieVideosResultsParcelable> movieVideosResultsParcelables) {
                movieVideosAdapter.setMovieVideos(movieVideosResultsParcelables);
            }
        });
    }

    private void onSaveFavoriteButtonClicked() {
        Intent intent = getIntent();
        MovieEntity movieEntity = new MovieEntity(
                intent.getStringExtra("title"),
                intent.getDoubleExtra("popularity", .0),
                intent.getIntExtra("voteCount", 0),
                intent.getStringExtra("posterPath"),
                intent.getStringExtra("backdropPath"),
                intent.getDoubleExtra("voteAverage", .0),
                intent.getStringExtra("overview"),
                intent.getStringExtra("releaseDate")
        );
        this.viewModel.saveFavoriteMovie(movieEntity);
    }

    private void onRemoveFavoriteButtonClicked(MovieEntity movieEntity) {
        this.viewModel.removeFavoriteMovie(movieEntity);
        final Button favoriteButton = findViewById(R.id.button_favorite);
        favoriteButton.setVisibility(View.VISIBLE);

        final Button removeFavoriteButton = findViewById(R.id.button_rm_favorite);
        removeFavoriteButton.setVisibility(View.GONE);

    }

    @Override
    public void onClick(MovieVideosResultsParcelable moviesVideosResultsParcelable) {
        String key = moviesVideosResultsParcelable.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key ));
        startActivity(intent);
    }
}
