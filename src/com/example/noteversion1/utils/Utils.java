package com.example.noteversion1.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.widget.Toast;

public class Utils 
{
	public static ArrayList<String> jsonArrayToList(JSONArray array) throws JSONException
	{
		ArrayList<String> list = new ArrayList<String>();     
		if (array != null) { 
		   int len = array.length();
		   for (int i=0;i<len;i++){ 
		    list.add(array.get(i).toString());
		   } 
		}
		
		return list;
	}
	
	public static void toastMessage(String message, Context context)
	{
		CharSequence text = message;
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
}
