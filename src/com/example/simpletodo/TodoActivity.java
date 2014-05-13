package com.example.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author Rajalakshmi Balakrishnan
 * @version 1.0
 * Main entry to the program
 * Displays the List of Todo Items and allows
 * you to add and remove items from the list.
 * 
 */
public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,items);
		lvItems.setAdapter(itemsAdapter);
		items.add("First Item");
		items.add("Second Item");
		setupListViewListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addTodoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}
	
	// Delete Item on onlong click
	// open detail view activity on click
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("position",position);
				i.putExtra("name",(String)parent.getItemAtPosition(position) );
				startActivityForResult(i, REQUEST_CODE);
				
			}
			
		});
	}
	
	/**
	 * Read items from a file
	 * @param None
	 * @return  None
	 */
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Persist items to the file
	 * @param None
	 * @return None
	 */
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile,items);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("name");
	     int position = data.getExtras().getInt("position");
	     items.set(position,name);
	     saveItems();
	     itemsAdapter.notifyDataSetChanged(); 
	  }
	} 
			
}
