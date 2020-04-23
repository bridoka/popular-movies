package com.emanuellerizzuto.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.popularmovies.data.MovieVideosResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosAdapterViewHolder> {
    private List<MovieVideosResultsParcelable> movieVideos;

    private final MovieVideosAdapter.MovieVideosAdapterOnClickHandler movieVideosAdapterOnClickHandler;

    public interface MovieVideosAdapterOnClickHandler{
        void onClick(MovieVideosResultsParcelable moviesVideosResultsParcelable);
    }

    public MovieVideosAdapter(MovieVideosAdapterOnClickHandler clickHandler) {
        movieVideosAdapterOnClickHandler = clickHandler;
    }

    public class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView tvVideoName;
        public MovieVideosAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVideoName = itemView.findViewById(R.id.tv_video_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieVideosResultsParcelable movieVideosResultsParcelable = movieVideos.get(adapterPosition);
            movieVideosAdapterOnClickHandler.onClick(movieVideosResultsParcelable);
        }

    }

    @NonNull
    @Override
    public MovieVideosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_video_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieVideosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideosAdapterViewHolder holder, int position) {
        holder.tvVideoName.setText(movieVideos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (movieVideos == null) {
            return 0;
        }
        return movieVideos.size();
    }

    public void setMovieVideos(ArrayList<MovieVideosResultsParcelable> movieVideosResultsParcelables) {
        movieVideos = movieVideosResultsParcelables;
        notifyDataSetChanged();
    }
}
