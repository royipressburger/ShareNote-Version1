package utils;

import java.util.ArrayList;

import NoteObjects.NoteContactInList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.idc.milab.mrnote.R;

public class ContactAdapter extends BaseAdapter implements Filterable {

    private int mResId;
    private ArrayList<NoteContactInList> mContactList;
    private ArrayList<NoteContactInList> mOriginalContactList;
    private LayoutInflater mInflater;

    public ContactAdapter(Context context, int resId, ArrayList<NoteContactInList> contactList) {

        this.mResId = resId;
        this.mOriginalContactList = contactList;
        this.mContactList = contactList;

        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public NoteContactInList getItem(int i) {
        return mContactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null) {

            view = mInflater.inflate(mResId, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.contactListItem_textview);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.contactListItem_checkbox);

        final NoteContactInList contact = mContactList.get(position);

        textView.setText(contact.getUserInfo().getName());
        checkBox.setChecked(contact.isSelected());

        return view;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // TODO Auto-generated method stub

                FilterResults filterResults = new FilterResults();

                // if constraint is empty return the original names
                if (constraint.length() == 0) {
                    filterResults.values = mOriginalContactList;
                    filterResults.count = mOriginalContactList.size();
                    return filterResults;
                }

                ArrayList<NoteContactInList> filteredContacts = new ArrayList();
                String filterString = constraint.toString().toLowerCase();
                String filterableString;

                for (int i = 0; i < mOriginalContactList.size(); i++) {

                	NoteContactInList contactToCheck = mOriginalContactList.get(i);

                    filterableString = contactToCheck.getUserInfo().getName();

                    if (filterableString.toLowerCase().contains(filterString.toLowerCase())) {

                        filteredContacts.add(contactToCheck);
                    }
                }

                filterResults.values = filteredContacts;
                filterResults.count = filteredContacts.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // TODO Auto-generated method stub

                mContactList = (ArrayList<NoteContactInList>) results.values;

                if(mContactList == null) {
                    mContactList = new ArrayList();
                }

                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
