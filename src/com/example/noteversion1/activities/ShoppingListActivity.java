package com.example.noteversion1.activities;

import AsyncTasks.AddItemToListTask;
import AsyncTasks.GetShoppingListByIdTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.noteversion1.R;
import com.example.noteversion1.utils.MyListView;
import com.example.noteversion1.utils.ShoppingList;

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
		new GetShoppingListByIdTask(this).execute("2");
		//new AddItemToListTask().execute("2", "fish");
	}

	@Override
	public void onButtonNextClicked() 
	{
		
	}

	public void asyncTaskGetListEndded() 
	{
		textViewListName.setText(shoppingList.getName());
		for (String item : shoppingList.getItems()) 
		{
			listViewItems.add(item);
		}
	}
	
	public void buttonAddItemClicked(View view)
	{
		String itemToAdd = editTextItemToAdd.getText().toString();
		new AddItemToListTask(this).execute("2", itemToAdd);
		editTextItemToAdd.setText("");
		
	}
	
	public void listWasUpdated()
	{
		new GetShoppingListByIdTask(this).execute("2");
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}
}
