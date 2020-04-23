package com.emanuellerizzuto.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.popularmovies.config.Config;
import com.emanuellerizzuto.popularmovies.data.MovieReviewsParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieReviewsResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {
    private List<MovieReviewsResultsParcelable> movieReviews;

    public class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvAuthor;
        public final TextView tvContent;
        public MovieReviewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    @NonNull
    @Override
    public MovieReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_review_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewsAdapterViewHolder holder, int position) {
        holder.tvAuthor.setText(movieReviews.get(position).getAuthor());
        holder.tvContent.setText(movieReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (movieReviews == null) {
            return 0;
        }
        return movieReviews.size();
    }

    public void setMovieReviews(ArrayList<MovieReviewsResultsParcelable> movieReviewsResultsParcelables) {
        movieReviews = movieReviewsResultsParcelables;
        notifyDataSetChanged();
    }
}
