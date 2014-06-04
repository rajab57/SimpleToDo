package com.example.simpletodo;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DueDatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	int mStartYear;
	int mStartMonth;
	int mStartDay;
	long mPrevSetTime = -1;

	DueDatePickerListener listener;
	TodoListRowAdapter mItemsAdapter;

	// Reference - http://stackoverflow.com/questions/18211684/how-to-transfer-the-formatted-date-string-from-my-datepickerfragment
	// http://developer.android.com/guide/components/fragments.html#CommunicatingWithActivity
	public interface DueDatePickerListener{
	    public void returnDate(int year, int month, int day);
	}
	
	DueDatePickerFragment(long epochTime) {
		mPrevSetTime = epochTime;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar c = Calendar.getInstance();
		if ( mPrevSetTime != -1 ) c.setTimeInMillis(mPrevSetTime);
		mStartYear = c.get(Calendar.YEAR);
		mStartMonth = c.get(Calendar.MONTH);
		mStartDay = c.get(Calendar.DAY_OF_MONTH);
		// Use the current date as the default date in the picker
		// TODO How do you get the activity from the Fragment ?
		listener = (DueDatePickerListener) getActivity();
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
				mStartYear, mStartMonth, mStartDay);
		//dialog.setTitle("Set Due Date");
		return dialog;
	}

	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// Note: month is 0 based.
		if (listener != null) 
		{
		  listener.returnDate(year, monthOfYear + 1, dayOfMonth); 

		}
		//mItemsAdapter.setDueDate(year, monthOfYear + 1, dayOfMonth);
		this.dismiss();
	}

}
