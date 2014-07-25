package com.pablolopezponce.diabetesslm.resources;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class MyRes extends Application 
{
	// Tag para el Log //
    public static final String TAG = "--->";

    public static SharedPreferences savedData;

    public static String userEmail;

    public static Typeface lightTypeface, regularTypeface;

    public static void saveSettingsDate(Context context)
    {
        long unixTime = System.currentTimeMillis() / 1000L;
        /* Pendiente de corregir, lo logico seria coger el valor com.pablolopezponce... de R.strings */
        savedData = context.getSharedPreferences("com.pablolopezponce.diabetesslm.savedata", context.MODE_PRIVATE);
        savedData.edit().putBoolean("updateSettings", true).apply();
    }
}
