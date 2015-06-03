package NoteObjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShoppingList implements Serializable
{
	private String name;
	private long startTime;
	private long endTime;
	private List<String> items;
	private ArrayList<NoteContact> users;
	private int lastReminder;
	private String opnerName;
	public String _id;

	public String calculateTimeLeft() 
	{
		String textToReturn = null;
		
		long diff = getEndTime() - Calendar.getInstance().getTimeInMillis();
		if (isDone())
		{
			textToReturn = "Done";
		}
		else
		{
			if (diff / 3600000 > 0)
			{
				if (diff / 86400000 > 0 )
					textToReturn = diff / 86400000 + "d";
				else
					textToReturn = diff / 3600000 + "h";
			}
			else
			{
				textToReturn = diff / 60000 + "m";
			}
		}

		return textToReturn;
	}
	
	public boolean isDone()
	{
		return (getEndTime() - Calendar.getInstance().getTimeInMillis()) <= 0;
	}

	public ShoppingList()
	{
		items = new ArrayList<String>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public ArrayList<NoteContact> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<NoteContact> users) {
		this.users = users;
	}
	public int getLastReminder() {
		return lastReminder;
	}
	public void setLastReminder(int lastReminder) {
		this.lastReminder = lastReminder;
	}

	public String getOpnerName() {
		return opnerName;
	}

	public void setOpnerName(String opnerName) {
		this.opnerName = opnerName;
	}

	public String toString() 
	{
		return String.format("%s, Opened on: %S", name, (new SimpleDateFormat("MMM dd,yyyy HH:mm")).format(new Date(startTime)));
	}
	
	public static int compare(ShoppingList list1, ShoppingList list2)
	{
		if (list1.isDone() && list2.isDone() || !list1.isDone() && !list2.isDone())
			return (int) (list2.getStartTime() - list1.getStartTime());
		
		if(list1.isDone())
			return 1;
		
		if (list2.isDone())
			return -1;
		
		return (int) (list2.getStartTime() - list1.getStartTime());
	}
}
