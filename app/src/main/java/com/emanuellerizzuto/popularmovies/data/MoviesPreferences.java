package com.emanuellerizzuto.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.emanuellerizzuto.popularmovies.R;

public class MoviesPreferences {
    public static String getSortOrder(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.sort_order_key);
        String defaultValue = context.getString(R.string.most_popular);

        return sharedPreferences.getString(key, defaultValue);
    }
}
