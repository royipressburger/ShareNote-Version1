package NoteObjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingList implements Serializable
{
	private String name;
	private long startTime;
	private long endTime;
	private List<String> items;
	private List<NoteContactInList> users;
	private int lastReminder;
	public String _id;
	
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
	public List<NoteContactInList> getUsers() {
		return users;
	}
	public void setUsers(List<NoteContactInList> users) {
		this.users = users;
	}
	public int getLastReminder() {
		return lastReminder;
	}
	public void setLastReminder(int lastReminder) {
		this.lastReminder = lastReminder;
	}
	
	public String toString() 
	{
		return String.format("Name: %s Open time: %S", name, (new SimpleDateFormat("MMM dd,yyyy HH:mm")).format(new Date(startTime)));
	}
}
