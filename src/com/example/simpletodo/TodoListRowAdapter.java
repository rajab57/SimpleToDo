package com.example.simpletodo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TodoListRowAdapter extends BaseAdapter {
	TodoItemsList items;
	FileHandlerInterface fileHandler;
	File filesDir;
	private int selectedPosition = -1;


	public TodoListRowAdapter(Context ctx, FileHandlerInterface fileHandler,File filesDir) {
		this.fileHandler = fileHandler;
		this.filesDir = filesDir;
		items = new TodoItemsList();
		items.setItems(fileHandler.parseItemsFromFile(filesDir));

	}


	@Override
	public View getView(final int index, View view, final ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.row, parent, false);

			final String listItem = items.get(index).getItemName();
			ViewHolder h = new ViewHolder();
			h.position = index;
			h.container = (LinearLayout)view.findViewById(R.id.container);
			h.textView = (TextView) view.findViewById(R.id.itemText);
			h.priorityImage =(ImageView) view.findViewById(R.id.priorityImage);
			h.textView.setText(listItem);
			
			h.itemDueDateView = (TextView) view.findViewById(R.id.itemDueDate);
			h.itemDueDateView.setText(items.get(index).getDueDateToDisplay());
			if (selectedPosition == index) {
				h.highLightHolder(parent.getContext());
			} else {
				h.unHighLightHolder();
			}
			h.setPriorityImage(parent.getContext(), items.get(index).getPriority());
			h.setFont(parent.getContext());
			view.setTag(h);
	
		}
		
		// Fill data
		final ViewHolder holder = (ViewHolder) view.getTag();
		holder.textView.setText(items.get(index).getItemName());
		holder.itemDueDateView.setText(items.get(index).getDueDateToDisplay());
		holder.setPriorityImage(parent.getContext(), items.get(index).getPriority());
		holder.setFont(parent.getContext());
		if (selectedPosition == index) {
			holder.highLightHolder(parent.getContext());
			
		} else {
			holder.unHighLightHolder();
		}

		return view;

	}
	
	public void setSelectedPosition(int pos) {
		if (selectedPosition == pos)
			selectedPosition = -1;
		else
			selectedPosition = pos;
		// inform the view of this change
		notifyDataSetChanged();
	}
	
	
	public void unsetSelectedPosition() {
		selectedPosition = -1;
		notifyDataSetChanged();
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	//
	// ADD AN ITEM ( name )
	// EDIT AN ITEM ( name )
	//
	public void add(String input) {
		items.add(new TodoItem(input));
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());
	}

	public void set(int position, String name) {
		TodoItem item = items.get(position);
		item.setItemName(name);
		items.set(position, item);
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());
	}

	//
	// Remove AN ITEM
	public void remove(int index) {
		items.remove(index);
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());
	}

	//
	// SET DUE DATE and PRIORITY on an item
	//
	public void setDueDate(int year, int monthOfYear, int dayOfMonth) {
		items.setDueDate(selectedPosition, year, monthOfYear, dayOfMonth);
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());
	}

	public void setPriority(String level) {
		items.setPriority(selectedPosition, level);
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());

	}
	
	
	public void setReminder(long milliseconds) {
		items.setReminder(selectedPosition, milliseconds);
		notifyDataSetChanged();
		fileHandler.saveItemsToFile(filesDir, items.getItems());
		
	}

	//
	// SORT
	//
	public void sortByPriority() {
		unsetSelectedPosition();
		items.sortByPriority();
		notifyDataSetChanged();
	}

	public void sortByDueDate() {
		unsetSelectedPosition();
		items.sortByDueDate();
		notifyDataSetChanged();
	}

	public void sortByLastAdded() {
		unsetSelectedPosition();
		items.sortByLastAdded();
		notifyDataSetChanged();
	}

	public TodoItem getSelectedItem() {
		assert (selectedPosition != -1);
		return items.get(getSelectedPosition());
	}

}
