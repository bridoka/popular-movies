package com.emanuellerizzuto.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieReviewsParcelable implements Parcelable {
    int page;
    int totalResults;
    int totalPages;
    ArrayList<MovieReviewsResultsParcelable> moviesReviewsResult;

    public MovieReviewsParcelable(int page, int totalResults, int totalPages,
                                  ArrayList<MovieReviewsResultsParcelable> resultsParcelables) {
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.moviesReviewsResult = resultsParcelables;
    }

    protected MovieReviewsParcelable(Parcel in) {
        page = in.readInt();
        totalResults = in.readInt();
        totalPages = in.readInt();
        moviesReviewsResult = in.createTypedArrayList(MovieReviewsResultsParcelable.CREATOR);
    }

    public static final Creator<MovieReviewsParcelable> CREATOR = new Creator<MovieReviewsParcelable>() {
        @Override
        public MovieReviewsParcelable createFromParcel(Parcel in) {
            return new MovieReviewsParcelable(in);
        }

        @Override
        public MovieReviewsParcelable[] newArray(int size) {
            return new MovieReviewsParcelable[size];
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
        dest.writeTypedList(moviesReviewsResult);
    }

    public ArrayList<MovieReviewsResultsParcelable> getMoviesReviewsResults() {
        return this.moviesReviewsResult;
    }
}
