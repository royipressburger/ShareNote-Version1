package com.example.noteversion1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPref 
{
	private static final String PREF_NAME = "com.example.mrnote.PREF";
	private static final String PREF_PREFIX = "com.example.mrnote.";
	private static SharedPreferences sharedPref;

	public static void initSharedPrefs(Context context) 
	{
		sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	public static void setSharedPrefsString(String key, String value)
	{
		Editor editor = sharedPref.edit();
		editor.putString(PREF_PREFIX + key, PREF_PREFIX + value);
		editor.apply();
	}

	/**
	 * @param key
	 * @param defValue - default value
	 * @return - The value that match the key or <defValue> if the key was not found
	 */
	public static String getSharedPrefsString(String key, String defValue) {
		String returnedValue = sharedPref.getString(PREF_PREFIX + key, PREF_PREFIX + defValue);
		return returnedValue.substring(returnedValue.lastIndexOf(".") + 1);
	}
}
