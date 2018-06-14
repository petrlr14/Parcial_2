package com.pdm00057616.gamenews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {


    public static void saveUserID(String id, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.apply();
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static void deleteSharePreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
