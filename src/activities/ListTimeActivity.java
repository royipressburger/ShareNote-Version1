package activities;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import utils.ConstService;
import NoteObjects.ShoppingList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import AsyncTasks.CreateListTask;

import com.google.gson.Gson;
import com.idc.milab.mrnote.R;

public class ListTimeActivity extends AbsractAppActivity 
{

	private ShoppingList listToCreate;
	private EditText lastReminder;
	private NumberPicker minuts;
	private NumberPicker hours;
	private NumberPicker day;
	private NumberPicker month;
	private String newListId;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_time);
		listToCreate = new Gson().fromJson(getIntent().getExtras().getString(ConstService.BUNDLE_NEW_LIST), ShoppingList.class);
		lastReminder = (EditText) findViewById(R.id.editTextReminder);
		createDateTimePicker();
	}

	private void createDateTimePicker() 
	{
		Calendar cal = Calendar.getInstance();
		minuts = (NumberPicker) findViewById(R.id.minuts);
		minuts.setMaxValue(59);
		minuts.setMinValue(00);
		minuts.setValue(cal.get(Calendar.MINUTE));
		hours = (NumberPicker) findViewById(R.id.hours);
		hours.setMaxValue(23);
		hours.setMinValue(0);
		hours.setValue(cal.get(Calendar.HOUR_OF_DAY));
		day = (NumberPicker) findViewById(R.id.day);
		month = (NumberPicker) findViewById(R.id.month);
		month.setMaxValue(11);
		month.setMinValue(0);
		month.setValue(cal.get(Calendar.MONTH));
		month.setDisplayedValues(new DateFormatSymbols().getMonths());
		MyOnValueChangeListener listner = new MyOnValueChangeListener(day, cal.get(Calendar.YEAR));
		listner.onValueChange(month, 0, cal.get(Calendar.MONTH));
		day.setValue(cal.get(Calendar.DAY_OF_MONTH));
		month.setOnValueChangedListener(listner);		
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

	private long getTimeInMilSecondsFromViews() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm", getResources().getConfiguration().locale);
		String dateInString = String.format("%s-%s-%s %s:%s",
				day.getValue(), month.getValue() + 1,Calendar.getInstance().get(Calendar.YEAR),
				hours.getValue(), minuts.getValue());
		
		try {
			Date date = sdf.parse(dateInString);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public void onButtonNextClicked(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
		//TODO: Create List and wait for the Id from the server.
		//createList();
		intent.putExtra(ConstService.BUNDLE_LIST_ID, newListId);
		startActivity(intent);
	}

	private void createList()
	{
		CreateListTask.OnFinishedListener listener = new CreateListTask.OnFinishedListener() {

			@Override
			public void onSuccess(String result) 
			{
				newListId = result;
				onButtonNextClicked(null);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		}; 

		CreateListTask task =  new CreateListTask(listener);
		task.execute(listToCreate);
	}

	private class MyOnValueChangeListener implements OnValueChangeListener
	{
		private NumberPicker mPicker;
		private int year;

		public MyOnValueChangeListener(NumberPicker picker, int year)
		{
			this.mPicker = picker;
			this.year = year;
		}

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
		{
			Calendar mycal = new GregorianCalendar(year, newVal, 1);
			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			mPicker.setDisplayedValues(null);
			mPicker.setMinValue(1);
			mPicker.setMaxValue(daysInMonth);			
		}

	}
}