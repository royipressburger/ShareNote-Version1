package com.example.noteversion1.activities;

import java.util.Calendar;

import AsyncTasks.CreateListTask;
import NoteObjects.ShoppingList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.idc.milab.mrnote.R;
import com.example.noteversion1.utils.ConstService;
import com.google.gson.Gson;

public class ListTimeActivity extends AbsractAppActivity 
{

	private ShoppingList listToCreate;
	private EditText lastReminder;
	private DatePicker datePicker;
	private TimePicker timePicker;
	
	private String newListId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_time);
		listToCreate = new Gson().fromJson(getIntent().getExtras().getString(ConstService.BUNDLE_NEW_LIST), ShoppingList.class);
		lastReminder = (EditText) findViewById(R.id.editTextReminder);
		timePicker = (TimePicker) findViewById(R.id.listTimeEnd);
		timePicker.setIs24HourView(true);
		datePicker = (DatePicker) findViewById(R.id.listDateEnd);
	}
	
	public void onButtonSetTimeClicked(View view)
	{
		//Get last reminder
		int lastReminderInMinuts = lastReminder.getText().toString().isEmpty() ? 0 : Integer.parseInt(lastReminder.getText().toString());

		listToCreate.setStartTime(Calendar.getInstance().getTimeInMillis());
		listToCreate.setEndTime(getTimeInMilSecondsFromViews());
		listToCreate.setLastReminder(lastReminderInMinuts);
		
		//Set will cause the list to be created.
		createList();
	}

	@Override
	public void onButtonNextClicked() 
	{
		Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
		//TODO: Create List and wait for the Id from the server.
		//createList();
		intent.putExtra(ConstService.BUNDLE_LIST_ID, newListId);
		startActivity(intent);
	}
	
	private long getTimeInMilSecondsFromViews()
	{
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();
		
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		
		
		Calendar date = Calendar.getInstance();
		//TODO: verify month zero based.
		date.set(year, month, day, hour, minute);
		return date.getTimeInMillis();
	}
	
	private void createList()
	{
		CreateListTask.OnFinishedListener listener = new CreateListTask.OnFinishedListener() {
			
			@Override
			public void onSuccess(String result) 
			{
				newListId = result;
				onButtonNextClicked();
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		}; 
		
		CreateListTask task =  new CreateListTask(listener);
		task.execute(listToCreate);
	}
}