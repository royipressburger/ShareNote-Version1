package utils;

import java.util.ArrayList;
import java.util.List;

import NoteObjects.NoteContact;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ContactListAdapter extends ArrayAdapter<NoteContact>
{

	private ArrayList<NoteContact> selectedContacts;
	public ContactListAdapter(Context context, int resource, List<NoteContact> items,ArrayList<NoteContact> selectedContacts) 
	{
		super(context, resource, items);
		this.selectedContacts = selectedContacts;  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		return super.getView(position, convertView, parent);
	}
}
