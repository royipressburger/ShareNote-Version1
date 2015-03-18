package com.example.noteversion1.activities;

import java.util.Date;

import com.example.noteversion1.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class ListTimeActivity extends AbsractAppActivity 
{

	private Date selectedDate;
	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_time);
		
		mTimePicker = (TimePicker) findViewById(R.id.listTimeEnd);
		mTimePicker.setIs24HourView(true);
		mDatePicker = (DatePicker) findViewById(R.id.listDateEnd);
		

	}
	
	public void onButtonSetTimeClicked(View view)
	{
		DatePicker datePicker = (DatePicker) findViewById(R.id.listDateEnd);
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();
		
		TimePicker timePicker = (TimePicker) findViewById(R.id.listTimeEnd);
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		
		selectedDate =  new Date(year, month, day, hour, minute, 0);
	}

	@Override
	public void onButtonNextClicked() 
	{
		Intent i = new Intent(getApplicationContext(), ShoppingListActivity.class);
		startActivity(i);
	}
}