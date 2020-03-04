package com.emanuellerizzuto.popularmovies.utilities;

import android.util.Log;

import com.emanuellerizzuto.popularmovies.data.MoviesParcelable;
import com.emanuellerizzuto.popularmovies.data.MoviesResultsParcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesJsonUtils {

    public static MoviesParcelable getPopularMoviesFromJson(int page) {
        URL popularMovies = NetworkUtils.buildPopularMoviesUrl(page);
        try {
            String response =  NetworkUtils.getResponseFromHttpUrl(popularMovies);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            int pageResult = jsonObject.getInt("page");
            int totalResults = jsonObject.getInt("total_results");
            int totalPages = jsonObject.getInt("total_pages");

            List<MoviesResultsParcelable> moviesResultsParcelablesList = new ArrayList<MoviesResultsParcelable>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultData = jsonArray.getJSONObject(i);
                double popularity = resultData.getDouble("popularity");
                int voteCount = resultData.getInt("vote_count");
                String posterPath = resultData.getString("poster_path");
                int id = resultData.getInt("id");
                String title = resultData.getString("title");
                double voteAverage = resultData.getDouble("vote_average");
                String overview = resultData.getString("overview");
                String releaseDate = resultData.getString("release_date");

                moviesResultsParcelablesList.add(new MoviesResultsParcelable(
                        popularity,
                        voteCount,
                        posterPath,
                        id,
                        title,
                        voteAverage,
                        overview,
                        releaseDate));

            }
            MoviesParcelable moviesParcelable = new MoviesParcelable(pageResult, totalResults, totalPages, moviesResultsParcelablesList);
            return moviesParcelable;
            //return jsonObject.getJSONArray("results").toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
