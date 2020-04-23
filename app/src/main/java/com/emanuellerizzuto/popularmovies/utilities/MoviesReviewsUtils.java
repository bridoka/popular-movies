package com.emanuellerizzuto.popularmovies.utilities;

import android.util.Log;

import com.emanuellerizzuto.popularmovies.data.MovieReviewsParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieReviewsResultsParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;
import com.emanuellerizzuto.popularmovies.database.MovieEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesReviewsUtils {

    public static MovieReviewsParcelable getMovieReviewsFromJson(int idMovie) {
        URL popularMovies = NetworkUtils.buildMovieReviewsUrl(idMovie);
        try {
            String response =  NetworkUtils.getResponseFromHttpUrl(popularMovies);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            int pageResult = jsonObject.getInt("page");
            int totalResults = jsonObject.getInt("total_results");
            int totalPages = jsonObject.getInt("total_pages");

            ArrayList<MovieReviewsResultsParcelable> moviesReviewsResultsParcelablesList = new ArrayList<MovieReviewsResultsParcelable>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultData = jsonArray.getJSONObject(i);
                String id = resultData.getString("id");
                String author = resultData.getString("author");
                String content = resultData.getString("content");

                moviesReviewsResultsParcelablesList.add(
                        new MovieReviewsResultsParcelable(id, author, content)
                );

            }
            MovieReviewsParcelable moviesParcelable =
                    new MovieReviewsParcelable(pageResult, totalResults, totalPages, moviesReviewsResultsParcelablesList);
            return moviesParcelable;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
