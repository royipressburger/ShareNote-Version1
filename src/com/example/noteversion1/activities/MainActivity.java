package com.example.noteversion1.activities;

import java.util.List;

import AsyncTasks.GetUserListsById;
import NoteObjects.ShoppingList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.example.noteversion1.R;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.MyListView;

public class MainActivity extends Activity {

	MyListView<ShoppingList> myLists;
	ListView myListsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myListsView = (ListView) findViewById(R.id.listViewMyLists);
		setListenerToList();
		myLists = new MyListView<ShoppingList>(this,
				android.R.layout.simple_list_item_1, myListsView, R.drawable.list_item);
		getMyLists();
//		myListsView.setBackgroundColor(-16776961);
		final Intent intent = new Intent(this, SetListNameActivity.class);
		ImageView img = (ImageView) findViewById(R.id.image3);
		img.setClickable(true);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});
	}

	public void startCreateListFlow(View view) {
		Intent intent = new Intent(this, SetListNameActivity.class);
		startActivity(intent);
	}

	private void getMyLists() {
		GetUserListsById.OnFinishedListener listener = new GetUserListsById.OnFinishedListener() {

			@Override
			public void onSuccess(List<ShoppingList> list) {
				for (ShoppingList shoppingList : list) {
					myLists.add(shoppingList);
				}
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		};

		GetUserListsById task = new GetUserListsById(listener);
		task.execute("someId");
	}

	private void setListenerToList() {
		myListsView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShoppingList selectedList = (ShoppingList) myListsView
						.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(),
						ShoppingListActivity.class);
				intent.putExtra(ConstService.BUNDLE_LIST_ID, selectedList._id);
				startActivity(intent);
			}
		});
	}
}
