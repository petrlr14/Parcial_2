package com.pdm00057616.gamenews.utils;

import android.content.Context;

import org.apache.commons.io.FileUtils;

public class ClearCache {

    public static void Clear(Context context) {
        FileUtils.deleteQuietly(context.getCacheDir());
    }
}
