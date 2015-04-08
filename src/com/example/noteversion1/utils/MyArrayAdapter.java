package com.example.noteversion1.utils;

import java.util.ArrayList;

import com.example.noteversion1.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter<T> extends ArrayAdapter<T> {
	private int id;

	public MyArrayAdapter(Context context, int resource, ArrayList<T> mItems, int id) {
		super(context, resource, mItems);
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = (TextView) super.getView(position, convertView, parent);
		view.setBackgroundResource(this.id);

		return view;
	}
}
