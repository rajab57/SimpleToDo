package com.example.simpletodo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoItemsList {
	ArrayList<TodoItem> mItems;
	public static enum  SortOrder { LASTADDED,DUEDATE,PRIORITY };
	public static enum DisplayDate { PastDue, Today, Tomorrow ,NextYear};
	private SortOrder mCurrentSortOrder = SortOrder.LASTADDED;
	
	TodoItemsList() {
		mItems = new ArrayList<TodoItem>();
	}

	public void setItems(List<TodoItem> list) {
		//Shallow copy
		mItems = new ArrayList<TodoItem>(list);
	}
	
	public final List<TodoItem> getItems() {
		return Collections.unmodifiableList(mItems);
	}
	
	public TodoItem getItem(int index) {
		return mItems.get(index);
	}
	
	public TodoItem get(int index) {
		return mItems.get(index);
	}
	
	public int size() {
		return mItems.size();
	}
	
	public void addItem(TodoItem item) {
		mItems.add(item);
	}
	
	public void add(TodoItem item) {
		mItems.add(item);
	}
	
	
	public void remove(int index) {
		mItems.remove(index);
	}
	
	public void setItem(int index, TodoItem item) {
		mItems.set(index, item);
	}
	
	public void set(int index, TodoItem item) {
		mItems.set(index, item);
	}
	
	public void setCurrentSortOrder(SortOrder order) {
		mCurrentSortOrder = order;
	}
	
	public SortOrder getCurrentSortOrder() {
		return mCurrentSortOrder;
	}
	
	public void sortByPriority() {
		setCurrentSortOrder(SortOrder.PRIORITY);
		Collections.sort(mItems,new ComparePriority());
		
	}
	
	public void sortByLastAdded() {
		setCurrentSortOrder(SortOrder.LASTADDED);
		Collections.sort(mItems,new CompareLastAdded());
	}
	
	public void sortByDueDate() {
		setCurrentSortOrder(SortOrder.DUEDATE);
		Collections.sort(mItems,new CompareDueDate());
	}
	
	public void setDueDate(int index, int year, int monthOfYear, int dayOfMonth) {
		assert(index != -1);
		TodoItem item = get(index);
		String month, day;
		if ( monthOfYear < 10 ) month = "0" + monthOfYear;
		else month = Integer.toString(monthOfYear);
		if ( dayOfMonth < 10 ) day = "0" + dayOfMonth;
		else day = Integer.toString(dayOfMonth);
		String duedate = year + "-" + month + "-" + day;
		item.setDueDate(duedate);
	}
	
	
	public void setDueDate(int index, String dueDate) {
		assert(index != -1);
		TodoItem item = get(index);
		item.setDueDate(dueDate);
	}
	
	public void setPriority(int index, String level) {
		assert (index != -1);
		TodoItem item = get(index);
		item.setPriority(TodoItem.PriorityLevels.valueOf(level) );
	}
	
	
	public void setReminder(int index, long milliseconds) {
		assert (index != -1);
		TodoItem item = get(index);
		item.setReminder(milliseconds);
	}
	
	
	public class ComparePriority implements Comparator<TodoItem>{
		 
	    @Override
	    public int compare(TodoItem o1, TodoItem o2) {
	    	// Descending order
	        return (o2.getPriority().ordinal() - o1.getPriority().ordinal() ); // Highest priority first
	    }
	}
	
	public class CompareLastAdded implements Comparator<TodoItem>{
		 
	    @Override
	    public int compare(TodoItem o1, TodoItem o2) {
	    	// Descending order
	        return (int) (o2.getTimestamp() > o1.getTimestamp() ? 1:-1 );   // Latest time first
	    }
	}
	
	public class CompareDueDate implements Comparator<TodoItem>{
		 
	    @Override
	    public int compare(TodoItem o1, TodoItem o2) {
	    	// Descending order
	        return (int) (o2.getDueDateInEpoch() < o1.getDueDateInEpoch() ? 1: -1);   // Closest Time first
	    }
	}
}
