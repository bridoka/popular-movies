package com.emanuellerizzuto.popularmovies.utilities;

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

public class MoviesJsonUtils {

    private static final String mostPopular = "most_popular";

    public static MoviesParcelable getMoviesFromJson(int page, String type) {
        URL popularMovies;
        if (type.equals(mostPopular)) {
            popularMovies = NetworkUtils.buildPopularMoviesUrl(page);
        } else {
            popularMovies = NetworkUtils.buildTopRatedUrl(page);
        }

        try {
            String response =  NetworkUtils.getResponseFromHttpUrl(popularMovies);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            int pageResult = jsonObject.getInt("page");
            int totalResults = jsonObject.getInt("total_results");
            int totalPages = jsonObject.getInt("total_pages");

            ArrayList<MoviesResultsParcelable> moviesResultsParcelablesList = new ArrayList<MoviesResultsParcelable>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultData = jsonArray.getJSONObject(i);
                double popularity = resultData.getDouble("popularity");
                int voteCount = resultData.getInt("vote_count");
                String posterPath = resultData.getString("poster_path");
                String backdropPath = resultData.getString("backdrop_path");
                int id = resultData.getInt("id");
                String title = resultData.getString("title");
                double voteAverage = resultData.getDouble("vote_average");
                String overview = resultData.getString("overview");
                String releaseDate = resultData.getString("release_date");

                moviesResultsParcelablesList.add(new MoviesResultsParcelable(
                        popularity,
                        voteCount,
                        posterPath,
                        backdropPath,
                        id,
                        title,
                        voteAverage,
                        overview,
                        releaseDate));

            }
            MoviesParcelable moviesParcelable = new MoviesParcelable(pageResult, totalResults, totalPages, moviesResultsParcelablesList);
            return moviesParcelable;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static MoviesParcelable getMoviesFromList(List<MovieEntity> movies) {
        ArrayList<MoviesResultsParcelable> moviesResultsParcelablesList = new ArrayList<MoviesResultsParcelable>();

        if (movies == null) {
            return null;
        }

        for(int i = 0 ; i < movies.size(); i++) {
            moviesResultsParcelablesList.add(new MoviesResultsParcelable(
                    movies.get(i).getPopularity(),
                    movies.get(i).getVoteCount(),
                    movies.get(i).getPosterPath(),
                    movies.get(i).getBackdropPath(),
                    movies.get(i).getId(),
                    movies.get(i).getTitle(),
                    movies.get(i).getVoteAverage(),
                    movies.get(i).getOverview(),
                    movies.get(i).getReleaseDate()));
        }
        MoviesParcelable moviesParcelable = new MoviesParcelable(movies.size(), movies.size(), 1, moviesResultsParcelablesList);
        return moviesParcelable;
    }
}
