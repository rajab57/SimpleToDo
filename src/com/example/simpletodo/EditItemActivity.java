package com.example.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Modify a single item in the todo List
 * @author Rajalakshmi Balakrishnan
 * @version 1.0
 * @since 2014-05-09
 * 
 */
public class EditItemActivity extends Activity {
	
	private String name;
	private int position;
	private EditText editItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 name = getIntent().getStringExtra("name");
		 position = getIntent().getIntExtra("position", 0);
		 setContentView(R.layout.activity_edit_item);
		 editItem = (EditText) findViewById(R.id.todoItem);
		 
		 editItem.setText(name);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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

	public void saveItem(View v) {
		Intent data = new Intent();
		  // Pass relevant data back as a result
		  data.putExtra("name", editItem.getText().toString());
		  data.putExtra("position", position);
		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data); // set result code and bundle data for response
		  finish(); // closes the activity, pass data to
		
	}


}
