package activities;

import java.util.ArrayList;

import utils.ConstService;
import utils.ContactAdapter;
import utils.MyListView;
import utils.SharedPref;
import utils.Utils;
import NoteObjects.NoteContact;
import NoteObjects.NoteContactInList;
import NoteObjects.ShoppingList;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

public class SelectUsersActivity extends AbsractAppActivity 
{
	private ShoppingList listToCreate;
	private ListView mListView;
	private ContactAdapter mContactAdapter;
	private ArrayList<NoteContactInList> mContactList;
	private SearchView mSearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_users);
		mContactList =  new ArrayList<NoteContactInList>();
		mContactAdapter = new ContactAdapter(this, R.layout.contact_list_item_with_checkbox, mContactList);

		mListView = (ListView) findViewById(R.id.contactsList);
		mSearchView = (SearchView) findViewById(R.id.searchView1);
		mListView.setAdapter(mContactAdapter);
		listToCreate = new Gson().fromJson(getIntent().getExtras().getString(ConstService.BUNDLE_NEW_LIST), ShoppingList.class);
		setSearchViewTextListener();
		setListViewOnClickedListener();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) 
	{
		for (NoteContactInList contact : getAllContacts()) 
		{
			mContactList.add(contact);
		}

		super.onPostCreate(savedInstanceState);
	}

	private ArrayList<NoteContactInList> getAllContacts()
	{
		String phoneNumber = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null); 
		ArrayList<NoteContactInList> contatcts = new ArrayList<NoteContactInList>();

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				NoteContact contact = new NoteContact();
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
				if (hasPhoneNumber > 0) 
				{
					contact.setName(name);
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					if (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						contact.setPhone(phoneNumber);
					}

					phoneCursor.close();
				}

				if (contact.getName() != null && contact.getPhone() != null)
					contatcts.add(new NoteContactInList(contact));
			}
		}

		return contatcts;
	}

	@Override
	public void onButtonNextClicked(View view) 
	{
		Intent intent = new Intent(this, ListTimeActivity.class);
		ArrayList<NoteContact> selectedUsrs = getSelectedUsers();
		listToCreate.setUsers(selectedUsrs);
		intent.putExtra(ConstService.BUNDLE_NEW_LIST, new Gson().toJson(listToCreate));
		startActivity(intent);
	}

	@SuppressLint("NewApi")
	private ArrayList<NoteContact> getSelectedUsers() 
	{
		ArrayList<NoteContact> selectedContats = new ArrayList<NoteContact>();
		
		for (NoteContactInList noteContact : mContactList) 
		{
			if (noteContact.isSelected())
			{
				NoteContact userInfo = noteContact.getUserInfo();
				userInfo.setPhone(PhoneNumberUtils.formatNumberToE164(userInfo.getPhone(), "IL"));
				selectedContats.add(noteContact.getUserInfo());
			}
		}
		
		NoteContact self = new NoteContact();
		self.setName("Me");
		self.setPhone(PhoneNumberUtils.formatNumberToE164((SharedPref.getSharedPrefsString(ConstService.PREF_PHONE_NUM, "0")), "IL"));
		selectedContats.add(self);
		return selectedContats;
	}

	private void setListViewOnClickedListener() {
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

				NoteContactInList contact = (NoteContactInList) mListView.getItemAtPosition(position);

				boolean selected = contact.isSelected();

				contact.setSelected(selected ? false : true);

				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();

				mContactAdapter.notifyDataSetChanged();
			}
		});
	}

	private void setSearchViewTextListener() {
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String arg0) 
			{
				mContactAdapter.getFilter().filter(arg0);
				mContactAdapter.notifyDataSetChanged();
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				mContactAdapter.getFilter().filter(query);
				mContactAdapter.notifyDataSetChanged();
				return false;
			}
		});
	}
}
