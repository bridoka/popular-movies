package com.emanuellerizzuto.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MoviesParcelable implements Parcelable {
    int page;
    int totalResults;
    int totalPages;
    List<MoviesResultsParcelable> moviesResult;

    public MoviesParcelable(int page, int totalResults, int totalPages,
                            List<MoviesResultsParcelable> resultsParcelables) {
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.moviesResult = resultsParcelables;
    }

    protected MoviesParcelable(Parcel in) {
        page = in.readInt();
        totalResults = in.readInt();
        totalPages = in.readInt();
        moviesResult = in.createTypedArrayList(MoviesResultsParcelable.CREATOR);
    }

    public static final Creator<MoviesParcelable> CREATOR = new Creator<MoviesParcelable>() {
        @Override
        public MoviesParcelable createFromParcel(Parcel in) {
            return new MoviesParcelable(in);
        }

        @Override
        public MoviesParcelable[] newArray(int size) {
            return new MoviesParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
        dest.writeTypedList(moviesResult);
    }

    public List<MoviesResultsParcelable> getMoviesResults() {
        return this.moviesResult;
    }
}
