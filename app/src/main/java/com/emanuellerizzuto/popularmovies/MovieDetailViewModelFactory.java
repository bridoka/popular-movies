package com.emanuellerizzuto.popularmovies;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    private final String title;
    private final int idMovie;

    public MovieDetailViewModelFactory(Context context, String title, int idMovie) {
        this.context = context;
        this.title = title;
        this.idMovie = idMovie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(context, title, idMovie);
    }
}
