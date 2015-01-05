package com.example.noteversion1.activities;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.example.noteversion1.R;
import com.example.noteversion1.utils.MyListView;
import com.example.noteversion1.utils.NoteContact;

public class SelectUsersActivity extends AbsractAppActivity 
{
	private MyListView<NoteContact> myList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_users);
		((SearchView) findViewById(R.id.searchView1)).setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextChange(String arg0) 
			{
				myList.getAdapter().getFilter().filter(arg0);
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				myList.getAdapter().getFilter().filter(query);
				return false;
			}
		});
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) 
	{
		myList = new MyListView<NoteContact>(this, android.R.layout.simple_list_item_multiple_choice, (ListView) findViewById(R.id.contactsList));
		
		myList.setChoiseMode(ListView.CHOICE_MODE_MULTIPLE);
		
		for (NoteContact contact : getAllContacts()) 
		{
			myList.add(contact);
		}
		
		super.onPostCreate(savedInstanceState);
	}
	private ArrayList<NoteContact> getAllContacts()
	{
		String phoneNumber = null;
		String email = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;
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
					contact.name = name;
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					if (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						contact.phone = phoneNumber;
					}
					phoneCursor.close();
					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
					if (emailCursor.moveToNext()) {
						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
						contact.email = email;
					}
					emailCursor.close();
				}
				
				if (contact.name != null && contact.phone != null)
					contatcts.add(contact);
			}
		}
		
		return contatcts;
	}

	@Override
	public void onButtonNextClicked() 
	{
		Intent intent = new Intent(this, ListTimeActivity.class);
		startActivity(intent);
	}
}
