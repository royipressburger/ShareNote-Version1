package activities;

import java.util.ArrayList;

import utils.Colors;
import utils.ConstService;
import utils.MyListView;
import utils.Utils;
import NoteObjects.NoteContact;
import NoteObjects.NoteContactInList;
import NoteObjects.ShoppingList;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

public class SelectUsersActivity extends AbsractAppActivity 
{
	private MyListView<NoteContact> contacts;
	private ShoppingList listToCreate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_users);
		listToCreate = new Gson().fromJson(getIntent().getExtras().getString(ConstService.BUNDLE_NEW_LIST), ShoppingList.class);
		((SearchView) findViewById(R.id.searchView1)).setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextChange(String arg0) 
			{
				contacts.getAdapter().getFilter().filter(arg0);
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				contacts.getAdapter().getFilter().filter(query);
				return false;
			}
		});
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) 
	{
		contacts = new MyListView<NoteContact>(this, android.R.layout.simple_list_item_multiple_choice, (ListView) findViewById(R.id.contactsList));
		
		contacts.setChoiseMode(ListView.CHOICE_MODE_MULTIPLE);
		
		for (NoteContact contact : getAllContacts()) 
		{
			contacts.add(contact);
		}
		
		super.onPostCreate(savedInstanceState);
	}
	private ArrayList<NoteContact> getAllContacts()
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
		ArrayList<NoteContact> contatcts = new ArrayList<NoteContact>();
		
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
					
					// Query and loop for every email of the contact
//					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
//					if (emailCursor.moveToNext()) {
//						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
//						contact.email = email;
//					}
//					emailCursor.close();
				}
				
				if (contact.getName() != null && contact.getPhone() != null)
					contatcts.add(contact);
			}
		}
		
		return contatcts;
	}

	@Override
	public void onButtonNextClicked(View view) 
	{
		Intent intent = new Intent(this, ListTimeActivity.class);
		listToCreate.setUsers(getSelectedUsers());
		intent.putExtra(ConstService.BUNDLE_NEW_LIST, new Gson().toJson(listToCreate));
		startActivity(intent);
	}

	private ArrayList<NoteContactInList> getSelectedUsers() 
	{
		SparseBooleanArray checked = contacts.getListView().getCheckedItemPositions();
		ArrayList<NoteContactInList> selectedContats = new ArrayList<NoteContactInList>();
		int color = 0;
		for (int i = 0; i < contacts.getListView().getAdapter().getCount(); i++) {
		    if (checked.get(i)) 
		    {
		    	try 
		    	{
					NoteContactInList contact = new NoteContactInList((NoteContact) contacts.getListView().getItemAtPosition(i), Utils.ColorsToAndroidColor(Colors.values()[color]));
					color++;
					selectedContats.add(contact);
				} 
		    	catch (Exception e) 
				{
					Utils.toastMessage(e.getMessage(), getApplicationContext());
				}
		    }
		}
		
		return selectedContats;
	}
}