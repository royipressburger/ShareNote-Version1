package activities;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import utils.ConstService;
import utils.MyListView;
import utils.SharedPref;
import AsyncTasks.GetUserListsById;
import NoteObjects.ShoppingList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.idc.milab.mrnote.R;

public class MainActivity extends ActionBarActivity {

	MyListView<ShoppingList> myLists;
	ListView myListsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setActionBar();
		myListsView = (ListView) findViewById(R.id.listViewMyLists);
		
		setListenerToList();
		setListView();
		getMyLists();
	}

	private void setActionBar() 
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.action_bar_main_layout, null);

		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void startCreateListFlow(View view) {
		Intent intent = new Intent(this, SetListNameActivity.class);
		startActivity(intent);
	}

	private void getMyLists() {
		GetUserListsById.OnFinishedListener listener = new GetUserListsById.OnFinishedListener() {

			@Override
			public void onSuccess(List<ShoppingList> list) 
			{
				for (ShoppingList shoppingList : list) 
				{
					myLists.add(shoppingList);
				}
				
				sortLists();
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
			}
		};

		GetUserListsById task = new GetUserListsById(listener);
		String param = SharedPref.getSharedPrefsString(ConstService.PREF_PHONE_NUM, null);
		
		task.execute(param);
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

	private void setListView() {
		myLists = new MyListView<ShoppingList>(this, R.layout.main_list_item, myListsView);
		ArrayAdapter<ShoppingList> listAdapter = new ArrayAdapter<ShoppingList>(getApplicationContext(), R.layout.main_list_item, R.id.listItemListOpener, myLists.getItems())
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
				View view = layoutInflater.inflate(R.layout.main_list_item, null, false);

				ShoppingList item = getItem(position);
				TextView listName = (TextView) view.findViewById(R.id.listItemListName);
				TextView listOpener = (TextView) view.findViewById(R.id.listItemListOpener);
				TextView endTime = (TextView) view.findViewById(R.id.listItemListDate);

				//TODO: setOpner!
				String date = (new SimpleDateFormat("dd/M")).format(new Date(item.getStartTime()));
				String timeLeft = item.calculateTimeLeft();
				if (timeLeft.equals("Done"))
				{
					((LinearLayout) view.findViewById(R.id.list_item_layout)).setBackgroundResource(R.drawable.list_item_done);
				}
				else
				{
					timeLeft = String.format("%s %s %s", timeLeft, "left", date);
				}
				
				listName.setText(item.getName());
				listOpener.setText("me");
				endTime.setText(timeLeft);
				return view;
			}};

		myLists.setAdapter(listAdapter);
		myListsView.setDivider(null);
		sortLists();
	}

	private void sortLists() {
		myLists.getAdapter().sort(new Comparator<ShoppingList>() {
			@Override 
			public int compare(ShoppingList arg1, ShoppingList arg0) 
			{
				return ShoppingList.compare(arg1, arg0);
			}
		});
	}
	
	public void onButtonAddClicked(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), SetListNameActivity.class);
		startActivity(intent);		
	}
}
