package activities;

import utils.ConstService;
import utils.Utils;
import NoteObjects.ShoppingList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

public class SetListNameActivity extends AbsractAppActivity {

	private EditText editTextListName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_list_name);
		editTextListName = (EditText) findViewById(R.id.editTextlistName);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/CALIBRI.TTF");
		editTextListName.setTypeface(typeFace);

		//Open keyboard on the list name edit text view.
		editTextListName.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	@Override
	public void onButtonNextClicked(View v) 
	{
		String listName = editTextListName.getText().toString();
		if (listName == null || listName.isEmpty() || listName.contains(getResources().getString(R.string.listNamePromptMessage)))
		{
			Utils.toastMessage("Please enter name for your list", getApplicationContext());
			return;
		}

		hideSoftKeyboard();
		Intent intent = new Intent(this, SelectUsersActivity.class);
		ShoppingList listToCreate = new ShoppingList();
		listToCreate.setName(listName);
		intent.putExtra(ConstService.BUNDLE_NEW_LIST, new Gson().toJson(listToCreate));
		startActivity(intent);
	}

	private void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(((ImageButton) findViewById(R.id.action_bar_item_next)).getWindowToken(), 0);
	}
}