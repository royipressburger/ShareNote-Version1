package com.example.noteversion1.activities;

import java.net.URI;

import org.json.JSONObject;

import AsyncTasks.AddItemToListTask;
import AsyncTasks.GetShoppingListByIdTask;
import AsyncTasks.GetShoppingListByIdTask.OnFinishedListener;
import NoteObjects.NoteContactInList;
import NoteObjects.ShoppingList;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.noteversion1.R;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.MyListView;
import com.example.noteversion1.utils.Utils;
import com.google.gson.Gson;

public class ShoppingListActivity extends AbsractAppActivity 
{
	private MyListView<String> listViewItems;
	private TextView textViewListName;
	private TextView textViewUsers;
	private EditText editTextItemToAdd;
	
	private ShoppingList shoppingList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_list);
		listViewItems = new MyListView<String>(this, android.R.layout.simple_expandable_list_item_1, (ListView) findViewById(R.id.listViewListItems));
		textViewListName = (TextView) findViewById(R.id.textViewListName);
		textViewUsers = (TextView) findViewById(R.id.textViewListUsers);
		editTextItemToAdd = (EditText) findViewById(R.id.editTextItem);
		String listId = getIntent().getExtras().getString(ConstService.BUNDLE_LIST_ID);
		launchGetListByIdTask(listId);
		createHandlerToGetListEveryFewSeconds(listId);
	}

	@Override
	public void onButtonNextClicked() 
	{
	}
	
	public void onButtonAddItemClicked(View view)
	{
		String itemToAdd = editTextItemToAdd.getText().toString();
		launchAddItemToListTask(shoppingList._id, itemToAdd);
		editTextItemToAdd.setText("");
	}
	
	public void onButtonCameraClicked(View view)
	{
		 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		 startActivityForResult(intent, 0);
	}
	
	public void listWasUpdated()
	{
		launchGetListByIdTask(shoppingList._id);
	}

	private void launchGetListByIdTask(String listId)
	{
		GetShoppingListByIdTask.OnFinishedListener onFinishedListener = new OnFinishedListener() {
			
			@Override
			public void onSuccess(JSONObject json) 
			{
				shoppingList = new Gson().fromJson(json.toString(), ShoppingList.class);
//				try {
//					
//					shoppingList.setName(json.getString(ConstService.LIST_NAME));
//					shoppingList.setItems(Utils.jsonArrayToList(json.getJSONArray(ConstService.LIST_ITEMS)));
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				textViewListName.setText(shoppingList.getName());
				for (String item : shoppingList.getItems()) 
				{
					listViewItems.add(item);
				}
				
				String users = "";
				for (NoteContactInList contact : shoppingList.getUsers()) {
					users += contact.getUserInfo().toString();
				}
				textViewUsers .setText(users);
			}
			
			@Override
			public void onError() 
			{
				Utils.toastMessage("Cannot get list", getApplicationContext());
			}
		};
		
		GetShoppingListByIdTask task = new GetShoppingListByIdTask(onFinishedListener);
		task.execute(listId);
	}
	
	private void launchAddItemToListTask(String listId, String itemToAdd)
	{
		AddItemToListTask.OnFinishedListener onFinishedListener = new AddItemToListTask.OnFinishedListener() 
		{
			@Override
			public void onSuccess() 
			{
				listWasUpdated();
			}
			
			@Override
			public void onError() 
			{
				Utils.toastMessage("Cannot add item", getApplicationContext());
			}
		};
		
		AddItemToListTask task = new AddItemToListTask(onFinishedListener);
		task.execute(listId, itemToAdd);
	}
	
	private void createHandlerToGetListEveryFewSeconds(final String listId)
	{
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				launchGetListByIdTask(listId);
			}
		}, 10000);
	}
}
