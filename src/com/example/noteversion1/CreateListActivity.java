package com.example.noteversion1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_list);
	}
	
	public void createList(View view){
//		MyListView newList = new MyListView(this,R.layout.abc_screen_simple,null);
		EditText editText = (EditText) findViewById(R.id.listName);
		String message = editText.getText().toString();
		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(message);

		// Set the text view as the activity layout
		setContentView(textView);
	}
	
}