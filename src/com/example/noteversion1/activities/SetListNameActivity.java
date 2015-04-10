package com.example.noteversion1.activities;

import NoteObjects.ShoppingList;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

import com.idc.milab.mrnote.R;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.Utils;
import com.google.gson.Gson;

public class SetListNameActivity extends AbsractAppActivity {

	private EditText editTextListName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_list_name);
		editTextListName = (EditText) findViewById(R.id.editTextlistName);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/CALIBRI.TTF");
		editTextListName.setTypeface(typeFace);
	}

	@Override
	public void onButtonNextClicked() 
	{
		String listName = editTextListName.getText().toString();
		if (listName == null || listName.isEmpty() || listName.contains(getResources().getString(R.string.listNamePromptMessage)))
		{
			Utils.toastMessage("Please enter name for your list", getApplicationContext());
			return;
		}
		
		Intent intent = new Intent(this, SelectUsersActivity.class);
		ShoppingList listToCreate = new ShoppingList();
		listToCreate.setName(listName);
		intent.putExtra(ConstService.BUNDLE_NEW_LIST, new Gson().toJson(listToCreate));
		startActivity(intent);
	}
	
//	public void createList(View view){
////		MyListView newList = new MyListView(this,R.layout.abc_screen_simple,null);
//		EditText editText = (EditText) findViewById(R.id.listName);
//		String message = editText.getText().toString();
//		// Create the text view
//		TextView textView = new TextView(this);
//		textView.setTextSize(40);
//		textView.setText(message);
//
//		// Set the text view as the activity layout
//		setContentView(textView);
//	}
	
	
}