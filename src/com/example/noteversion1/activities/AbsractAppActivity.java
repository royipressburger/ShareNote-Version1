package com.example.noteversion1.activities;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.noteversion1.R;

public abstract class AbsractAppActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_absract_app);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    @SuppressWarnings("deprecation")
		BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.id.image4)); 
//	    background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
	    actionBar.setBackgroundDrawable(background);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.abstract_activity_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_next_button:
	            onButtonNextClicked();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public abstract void onButtonNextClicked();
}