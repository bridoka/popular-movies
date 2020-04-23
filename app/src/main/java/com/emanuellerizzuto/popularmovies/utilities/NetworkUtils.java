package com.emanuellerizzuto.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.emanuellerizzuto.popularmovies.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String API_KEY_PARAM = "api_key";

    private static final String PAGE_PARAM = "page";

    private static final String LANGUAGE_PARAM = "language";

    public static URL buildPopularMoviesUrl(int page) {
        Uri buildUri = Uri.parse(Config.POPULAR_MOVIES_DB_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Config.API_KEY)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .appendQueryParameter(LANGUAGE_PARAM, Config.LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTopRatedUrl(int page) {
        Uri buildUri = Uri.parse(Config.TOP_RATED_MOVIES_DB_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Config.API_KEY)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .appendQueryParameter(LANGUAGE_PARAM, Config.LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildMovieReviewsUrl(int idMovie) {
        String reviewUrl = Config.MOVIE_DB_URL_BASE + "/" + String.valueOf(idMovie) + "/reviews";
        Uri buildUri = Uri.parse(reviewUrl).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Config.API_KEY)
                .appendQueryParameter(PAGE_PARAM, "1")
                .appendQueryParameter(LANGUAGE_PARAM, Config.LANGUAGE)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildMovieVideosUrl(int idMovie) {
        String reviewUrl = Config.MOVIE_DB_URL_BASE + "/" + String.valueOf(idMovie) + "/videos";
        Uri buildUri = Uri.parse(reviewUrl).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Config.API_KEY)
                .appendQueryParameter(PAGE_PARAM, "1")
                .appendQueryParameter(LANGUAGE_PARAM, Config.LANGUAGE)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        try {
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            scanner.close();
            urlConnection.disconnect();
        }
    }
}
