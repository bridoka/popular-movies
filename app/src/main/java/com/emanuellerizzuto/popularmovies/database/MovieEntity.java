package com.emanuellerizzuto.popularmovies.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    double popularity;
    int voteCount;
    String posterPath;
    String backdropPath;
    double voteAverage;
    String overview;
    String releaseDate;

    @Ignore
    public MovieEntity(String title,
                       double popularity,
                       int voteCount,
                       String posterPath,
                       String backdropPath,
                       double voteAverage,
                       String overview,
                       String releaseDate) {

        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public MovieEntity(int id,
                       String title,
                       double popularity,
                       int voteCount,
                       String posterPath,
                       String backdropPath,
                       double voteAverage,
                       String overview,
                       String releaseDate) {

        this.id = id;
        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
