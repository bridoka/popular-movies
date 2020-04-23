package com.emanuellerizzuto.popularmovies.utilities;

import android.util.Log;

import com.emanuellerizzuto.popularmovies.data.MovieVideosParcelable;
import com.emanuellerizzuto.popularmovies.data.MovieVideosResultsParcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieVideosUtils {

    public static MovieVideosParcelable getMovieVideosFromJson(int idMovie) {
        URL popularMovies = NetworkUtils.buildMovieVideosUrl(idMovie);
        try {
            String response =  NetworkUtils.getResponseFromHttpUrl(popularMovies);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            int id = jsonObject.getInt("id");

            ArrayList<MovieVideosResultsParcelable> movieVideosResultsParcelablesList = new ArrayList<MovieVideosResultsParcelable>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultData = jsonArray.getJSONObject(i);
                String idVideo = resultData.getString("id");
                String key = resultData.getString("key");
                String name = resultData.getString("name");
                String site = resultData.getString("site");
                int size = resultData.getInt("size");
                String type = resultData.getString("type");

                movieVideosResultsParcelablesList.add(
                        new MovieVideosResultsParcelable(idVideo, key, name, site, size, type)
                );
            }
            return new MovieVideosParcelable(id, movieVideosResultsParcelablesList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
