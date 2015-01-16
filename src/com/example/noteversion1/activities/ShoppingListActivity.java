package com.example.noteversion1.activities;

import org.json.JSONException;
import org.json.JSONObject;

import AsyncTasks.AddItemToListTask;
import AsyncTasks.GetShoppingListByIdTask;
import AsyncTasks.GetShoppingListByIdTask.OnFinishedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteversion1.R;
import com.example.noteversion1.utils.ConstService;
import com.example.noteversion1.utils.MyListView;
import com.example.noteversion1.utils.ShoppingList;
import com.example.noteversion1.utils.Utils;

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
		launchGetListByIdTask("2");
		//new AddItemToListTask().execute("2", "fish");
	}

	@Override
	public void onButtonNextClicked() 
	{
		
	}
	
	public void buttonAddItemClicked(View view)
	{
		String itemToAdd = editTextItemToAdd.getText().toString();
		launchAddItemToListTask("2", itemToAdd);
		editTextItemToAdd.setText("");
		
	}
	
	public void listWasUpdated()
	{
		launchGetListByIdTask("2");
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	private void launchGetListByIdTask(String listId)
	{
		GetShoppingListByIdTask.OnFinishedListener onFinishedListener = new OnFinishedListener() {
			
			@Override
			public void onSuccess(JSONObject json) 
			{
				shoppingList = new ShoppingList();
				try {
					shoppingList.setName(json.getString(ConstService.LIST_NAME));
					shoppingList.setItems(Utils.jsonArrayToList(json.getJSONArray(ConstService.LIST_ITEMS)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				textViewListName.setText(shoppingList.getName());
				for (String item : shoppingList.getItems()) 
				{
					listViewItems.add(item);
				}
			}
			
			@Override
			public void onError() 
			{
				toastMessage("Cannot get list");
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
				toastMessage("Cannot add item")	;
			}
		};
		
		AddItemToListTask task = new AddItemToListTask(onFinishedListener);
		task.execute(listId, itemToAdd);
	}
	
	private void toastMessage(String message)
	{
		CharSequence text = message;
		Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
		toast.show();
	}
}
