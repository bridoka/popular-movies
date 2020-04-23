package com.emanuellerizzuto.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReviewsResultsParcelable implements Parcelable {

    String id;
    String author;
    String content;

    public MovieReviewsResultsParcelable(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    protected MovieReviewsResultsParcelable(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<MovieReviewsResultsParcelable> CREATOR = new Creator<MovieReviewsResultsParcelable>() {
        @Override
        public MovieReviewsResultsParcelable createFromParcel(Parcel in) {
            return new MovieReviewsResultsParcelable(in);
        }

        @Override
        public MovieReviewsResultsParcelable[] newArray(int size) {
            return new MovieReviewsResultsParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
