package com.example.noteversion1.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

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
}
