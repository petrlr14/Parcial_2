package com.pdm00057616.gamenews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pdm00057616.gamenews.R;

public class SharedPreferencesUtils {


    public static void saveUserID(String id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.user_id), id);
        editor.apply();
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.token), token);
        editor.apply();
    }

    public static void deleteSharePreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static String getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);
        return preferences.getString(context.getString(R.string.user_id), "");
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);
        if (preferences.contains(context.getString(R.string.token))) {
            return preferences.getString(context.getString(R.string.token), "");
        }
        return "";
    }

}
