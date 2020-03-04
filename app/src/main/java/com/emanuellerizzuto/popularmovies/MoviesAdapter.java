package com.emanuellerizzuto.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.popularmovies.config.Config;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private List<MoviesResultsParcelable> moviesResults;

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivMovies;

        public MoviesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovies = itemView.findViewById(R.id.iv_movie_item);
        }
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {
        String imgMovie = moviesResults.get(position).getPosterPath();
        Picasso.get().load(Config.MOVIES_IMAGES_URL+imgMovie).into(holder.ivMovies);
    }

    @Override
    public int getItemCount() {
        if (moviesResults == null) {
            return 0;
        }
        return moviesResults.size();
    }

    public void setMoviesResults(List<MoviesResultsParcelable> moviesResultsParcelables) {
        moviesResults = moviesResultsParcelables;
        notifyDataSetChanged();
    }
}
