package com.example.simpletodo;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.simpletodo.PriorityDialogFragment.AlertPositiveListener;

/**
 * Modify a single item in the todo List
 * 
 * @author Rajalakshmi Balakrishnan
 * @version 1.0
 * @since 2014-05-09
 * 
 */
public class EditItemActivity extends Activity implements
		DueDatePickerFragment.DueDatePickerListener, AlertPositiveListener {

	// private String name;
	private int position;
	private EditText editItem;
	private TodoItem todoItem;
	private TextView editDueDate;
	private TextView editPriority;
	private TextView editReminder;
	private long reminderInEpoch = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		// name = getIntent().getStringExtra("name");
		position = getIntent().getIntExtra("position", 0);
		todoItem = (TodoItem) getIntent().getSerializableExtra("item");

		editItem = (EditText) findViewById(R.id.editTodoItem);
		editItem.setText(todoItem.getItemName());

		editDueDate = (TextView) findViewById(R.id.editDueDate);
		editDueDate.setText(todoItem.getDueDate());

		editPriority = (TextView) findViewById(R.id.editPriority);
		editPriority.setText(todoItem.getPriority().name());

		editReminder = (TextView) findViewById(R.id.editReminder);
		if (todoItem.getReminder() > 1)
			editReminder.setText(TodoItem.formatDate(todoItem.getReminder()));
		else
			editReminder.setText("None");

		editDueDate.setClickable(true);
		editDueDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment dialogFragment = new DueDatePickerFragment(
						todoItem.getDueDateInEpoch());
				dialogFragment
						.show(getFragmentManager(), "detailduedatedialog");
			}
		});

		editReminder.setClickable(true);
		editReminder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				viewAlarm(); // TODO remove duplicate from TodoActivity.java
			}
		});

		editPriority.setClickable(true);
		editPriority.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment dialogFragment = new PriorityDialogFragment(
						todoItem.getPriority().ordinal());
				dialogFragment.show(getFragmentManager(),
						"detailprioritydialog");

			}
		});

	}

	// Duplicate from TodoActivity
	public void viewAlarm() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.date_time_picker);
		dialog.setTitle("Set Reminder");
		Button saveDate = (Button) dialog.findViewById(R.id.btnSave);
		saveDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				DatePicker dp = (DatePicker) dialog
						.findViewById(R.id.datePicker1);
				TimePicker tp = (TimePicker) dialog
						.findViewById(R.id.timePicker1);
				Calendar cal = Calendar.getInstance();
				// cal.setTimeZone(TimeZone.getTimeZone("PST"));
				cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
						tp.getCurrentHour(), tp.getCurrentMinute());
				editReminder.setText(TodoItem.formatDateTime(cal
						.getTimeInMillis()));
				reminderInEpoch = cal.getTimeInMillis();
			}
		});
		dialog.show();
	}

	public void saveItem(View v) {
		Intent data = new Intent();
		String name = editItem.getText().toString();
		// Pass relevant data back as a result
		if (name.matches("")) {
			Toast.makeText(this, "You did not enter a username",
					Toast.LENGTH_SHORT).show();
			return;
		}

		data.putExtra("name", editItem.getText().toString());
		data.putExtra("position", position);
		data.putExtra("duedate", editDueDate.getText().toString());
		data.putExtra("reminder", reminderInEpoch);
		data.putExtra("priority", editPriority.getText().toString());
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for
									// response
		finish(); // closes the activity, pass data to

	}

	// call back to duedate dialog. How do you identify where the callback
	// originated from.
	@Override
	public void returnDate(int year, int month, int day) {
		editDueDate.setText(TodoItem.formatDate(year, month, day));

	}

	// call back from priority Dialog
	@Override
	public void onPositiveClick(String position) {
		editPriority.setText(position);
	}

}
