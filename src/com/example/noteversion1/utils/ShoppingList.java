package com.example.noteversion1.utils;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList 
{
	private String name;
	private long creationTime;
	private long endTime;
	private List<String> items;
	//TODO: add users, items
	
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
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
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
}
