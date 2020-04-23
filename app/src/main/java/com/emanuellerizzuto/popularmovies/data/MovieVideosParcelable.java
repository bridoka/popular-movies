package com.emanuellerizzuto.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieVideosParcelable implements Parcelable {
    int id;
    ArrayList<MovieVideosResultsParcelable> moviesVideosResult;

    public MovieVideosParcelable(int id, ArrayList<MovieVideosResultsParcelable> resultsParcelables) {
        this.id = id;
        this.moviesVideosResult = resultsParcelables;
    }

    protected MovieVideosParcelable(Parcel in) {
        id = in.readInt();
        moviesVideosResult = in.createTypedArrayList(MovieVideosResultsParcelable.CREATOR);
    }

    public static final Creator<MovieVideosParcelable> CREATOR = new Creator<MovieVideosParcelable>() {
        @Override
        public MovieVideosParcelable createFromParcel(Parcel in) {
            return new MovieVideosParcelable(in);
        }

        @Override
        public MovieVideosParcelable[] newArray(int size) {
            return new MovieVideosParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(moviesVideosResult);
    }

    public ArrayList<MovieVideosResultsParcelable> getMovieVideosResults() {
        return this.moviesVideosResult;
    }
}
