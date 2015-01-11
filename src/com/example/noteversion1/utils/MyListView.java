package com.example.noteversion1.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListView<T>  
{
	private ArrayList<T> mItems;
	private ArrayAdapter<T> mAdapter;
	private ListView mListView;
	
	/**
	 * Create a generic list view
	 * @param contex - the activity that contains the list
	 * @param layout - example (android.R.layout.simple_list_item_1)
	 * @param listView - the id of the listView in the given activity.
	 */
	public MyListView(Activity contex, int layout, ListView listView)
	{
		mListView = listView;
		mItems = new ArrayList<T>();
		mAdapter = new ArrayAdapter<T>(contex, layout, mItems);
		mListView.setAdapter(mAdapter);
	}
	
	/**
	 * Adding the item
	 * Also notify to the adapter.
	 * @param objectToAdd
	 */
	public void add(T objectToAdd)
	{
		if (!mItems.contains(objectToAdd))
		{
			mItems.add(objectToAdd);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void setChoiseMode(int mode)
	{
		mListView.setChoiceMode(mode);   
		mListView.setItemsCanFocus(false);
	}

	public ArrayAdapter<T> getAdapter() {
		return mAdapter;
	}
}