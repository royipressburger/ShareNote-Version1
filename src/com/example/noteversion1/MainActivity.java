package com.example.noteversion1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

//	MyListView myLists = new MyListView(this, 0, null);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void createNewList(View view) {
		Intent intent = new Intent(this, CreateListActivity.class);
		startActivity(intent);
		}
}
