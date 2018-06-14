package com.pdm00057616.gamenews.utils;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class ClearCache {

    public static void Clear(Context context) {
        FileUtils.deleteQuietly(context.getCacheDir());
    }
}
