package com.emanuellerizzuto.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesPreferences;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.utilities.MoviesJsonUtils;

import java.util.ArrayList;

public class MovieViewModel extends AndroidViewModel {

    Context context;

    private MutableLiveData<ArrayList<MoviesResultsParcelable>> moviesResultsParcelableList = new MutableLiveData<>();

    public MutableLiveData<ArrayList<MoviesResultsParcelable>> getMoviesResultsParcelableList(){
        return moviesResultsParcelableList;
    }

    public void executeTask() {
        AddAsyncTask asyncTask = new AddAsyncTask();
        asyncTask.execute();
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        executeTask();
    }

    private class AddAsyncTask extends AsyncTask<Void, Void, MoviesParcelable>{
        @Override
        protected MoviesParcelable doInBackground(Void... params) {
            String orderType = MoviesPreferences.getSortOrder(context);
            if (orderType == null) {
                orderType = "top_rated";
            }
            return MoviesJsonUtils.getMoviesFromJson(1, orderType);
        }

        @Override
        protected void onPostExecute(MoviesParcelable moviesParcelable) {
            if (moviesParcelable != null) {
                moviesResultsParcelableList.setValue(moviesParcelable.getMoviesResults());
            }
        }
    }
}
