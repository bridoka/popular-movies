package com.emanuellerizzuto.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MoviesResultsParcelable implements Parcelable {

    double popularity;
    int voteCount;
    String posterPath;
    String backdropPath;
    int id;
    String title;
    double voteAverage;
    String overview;
    String releaseDate;

    public MoviesResultsParcelable(double popularity,
                                   int voteCount,
                                   String posterPath,
                                   String backdropPath,
                                   int id,
                                   String title,
                                   double voteAverage,
                                   String overview,
                                   String releaseDate) {

        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }

    protected MoviesResultsParcelable(Parcel in) {
        popularity = in.readDouble();
        voteCount = in.readInt();
        posterPath = in.readString();
        backdropPath = in.readString();
        id = in.readInt();
        title = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MoviesResultsParcelable> CREATOR = new Creator<MoviesResultsParcelable>() {
        @Override
        public MoviesResultsParcelable createFromParcel(Parcel in) {
            return new MoviesResultsParcelable(in);
        }

        @Override
        public MoviesResultsParcelable[] newArray(int size) {
            return new MoviesResultsParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {return backdropPath; }

    public int getId() {
        return id;
    }

    public String getTitle() { return title; }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
