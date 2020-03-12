package com.emanuellerizzuto.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;

import com.emanuellerizzuto.popularmovies.config.Config;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

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
            tvVoteAverage.setText(intent.getStringExtra("voteAverage") + "/10");
            Picasso.get()
                    .load(Config.MOVIES_IMAGES_URL+intent.getStringExtra("posterPath"))
                    .into(ivPath);
        }
    }
}
