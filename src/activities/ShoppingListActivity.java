package activities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import utils.ConstService;
import utils.MyListView;
import utils.SharedPref;
import utils.Utils;
import AsyncTasks.AddItemToListTask;
import AsyncTasks.GetShoppingListByIdTask;
import AsyncTasks.GetShoppingListByIdTask.OnFinishedListener;
import NoteObjects.NoteContact;
import NoteObjects.ShoppingList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

public class ShoppingListActivity extends AbsractAppActivity 
{
	private MyListView<String> listViewItems;
	private TextView textViewListName;
	private TextView textViewUsers;
	private TextView textViewTimeLeft;
	private EditText editTextItemToAdd;

	private ShoppingList shoppingList;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_shopping_list);
		listViewItems = new MyListView<String>(this, R.layout.shopping_list_items, (ListView) findViewById(R.id.listViewListItems));
		createCustomAdapter();
		textViewListName = (TextView) findViewById(R.id.textViewListName);
		textViewUsers = (TextView) findViewById(R.id.textViewListUsers);
		textViewTimeLeft = (TextView)findViewById(R.id.textViewTimeLeft);

		// Remove the next button from action bar
		ImageButton button = ((ImageButton) findViewById(R.id.action_bar_item_next));
		button.setVisibility(View.INVISIBLE);
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
						users += usersList.get(i).toString()+ " | ";
					}
					else{
						users += usersList.get(i).toString();
					}
				}

				textViewUsers.setText(users);
				textViewTimeLeft.setText(shoppingList.calculateTimeLeft());
			}

			@Override
			public void onError(String error) 
			{
				Utils.toastMessage("Cannot get list " + error, getApplicationContext());
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
		String userNick = SharedPref.getSharedPrefsString(ConstService.PREF_USER_NICK, ConstService.PREF_DEFAULT);
		if (!userNick.equals(ConstService.PREF_DEFAULT))
		{
			userNick = ConstService.ITEM_ADDER_SEPERATOR + userNick;
		}
		else
		{
			userNick = "";
		}
		task.execute(listId, itemToAdd + userNick);
	}

	private void createCustomAdapter()
	{
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.id.textViewItemName, listViewItems.getItems())
				{
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
				View view = layoutInflater.inflate(R.layout.shopping_list_items, null, false);

				String item = getItem(position);
				TextView itemName = (TextView) view.findViewById(R.id.textViewItemName);
				TextView itemAdder = (TextView) view.findViewById(R.id.textViewItemAdder);
				String adder = "";
				Pattern pattern = Pattern.compile(ConstService.ITEM_ADDER_PATTERN);
				Matcher matcher = pattern.matcher(item);
				if (matcher.find())
				{
					if (matcher.group(1) == null ) 
					{
						adder = "";
					}
					else
					{
						adder = matcher.group(1);
						item = new StringBuilder(item).replace(matcher.start(), matcher.end(),"").toString();
					}
				}

				if (adder.equals(SharedPref.getSharedPrefsString(ConstService.PREF_USER_NICK, ConstService.PREF_DEFAULT)))
				{
					adder = "Me";
				}
				itemAdder.setText(adder);
				itemName.setText(item);
				return view;
			}};

			listViewItems.setAdapter(listAdapter);
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
