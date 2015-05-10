package activities;

import java.util.ArrayList;

import org.json.JSONObject;

import utils.ConstService;
import utils.MyListView;
import utils.Utils;
import AsyncTasks.AddItemToListTask;
import AsyncTasks.GetShoppingListByIdTask;
import AsyncTasks.GetShoppingListByIdTask.OnFinishedListener;
import NoteObjects.NoteContact;
import NoteObjects.ShoppingList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

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
		//set font Calibri to text views
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/CALIBRI.TTF");
		textViewListName.setTypeface(typeFace);
		textViewUsers.setTypeface(typeFace);
		editTextItemToAdd = (EditText) findViewById(R.id.editTextItem);
		String listId = getIntent().getExtras().getString(ConstService.BUNDLE_LIST_ID);
		launchGetListByIdTask(listId);
		createHandlerToGetListEveryFewSeconds(listId);
	}

	@Override
	public void onButtonNextClicked(View v) 
	{
	}
	
	public void onButtonAddItemClicked(View view)
	{
		String itemToAdd = editTextItemToAdd.getText().toString();
		launchAddItemToListTask(shoppingList._id, itemToAdd);
		editTextItemToAdd.setText("");
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
				textViewListName.setText(shoppingList.getName());
				for (String item : shoppingList.getItems()) 
				{
					listViewItems.add(item);
				}
				
				String users = "";
				ArrayList<NoteContact> usersList = shoppingList.getUsers();
				for (int i = 0; i < usersList.size(); i++) {
					if(i != (usersList.size() - 1)){
						users += usersList.get(i).toString()+", ";
					}
					else{
						users += usersList.get(i).toString();
					}
				}
				textViewUsers.setText(users);
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
