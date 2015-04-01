package com.example.noteversion1.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import NoteObjects.ShoppingList;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.google.gson.Gson;

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
	
	public static String shoppingListToCreateToJson(ShoppingList listToCreate) throws JSONException
	{
		JSONObject json = new JSONObject(new Gson().toJson(listToCreate));
		JSONArray usersArray = json.getJSONArray(ConstService.LIST_USERS);
		json.remove("_id");
		for (int i = 0; i < usersArray.length(); i++) 
		{
			usersArray.getJSONObject(i).remove("name");
		}
		
		return json.toString();
	}
	
	public static void toastMessage(String message, Context context)
	{
		CharSequence text = message;
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static int ColorsToAndroidColor(Colors color) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException
	{
		return Color.class.getField(color.name()).getInt(null);
	}
}
