package activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import utils.ConstService;
import utils.MyListView;
import NoteObjects.ShoppingList;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import asyncTasks.GetUserListsById;

import com.idc.milab.mrnote.R;

public class MainActivity extends AbsractAppActivity {

	MyListView<ShoppingList> myLists;
	ListView myListsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myListsView = (ListView) findViewById(R.id.listViewMyLists);
		setListenerToList();

		setListView();

		myListsView.setDivider(null);
		getMyLists();
	}

	@Override
	protected void setLayoutToActionBar(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_action_bar, menu);
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

				myLists.getAdapter().sort(new Comparator<ShoppingList>() {
					@Override 
					public int compare(ShoppingList arg1, ShoppingList arg0) 
					{
						return (int) (arg0.getStartTime() - arg1.getStartTime());
					}
				});
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

	private void setListView() {
		myLists = new MyListView<ShoppingList>(this, R.layout.main_list_item, myListsView, R.drawable.list_item);
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
				String timeLeft = item.calculateTimeLeft();
				if (timeLeft.equals("Done"))
				{
					((LinearLayout) view.findViewById(R.id.list_item_layout)).setBackgroundResource(R.drawable.list_item_done);
				}
				listName.setText(item.getName());
				listOpener.setText("me");
				endTime.setText(timeLeft);
				return view;
			}};

		myLists.setAdapter(listAdapter);
	}

	@Override
	public void onButtonNextClicked() 
	{
		Intent intent = new Intent(getApplicationContext(), SetListNameActivity.class);
		startActivity(intent);		
	}
}
