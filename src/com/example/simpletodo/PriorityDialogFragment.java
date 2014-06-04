package com.example.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class PriorityDialogFragment extends DialogFragment{
	
	private int mSelectedPriority = 1;
	private final CharSequence[] items = { "LOW", "MEDIUM", "HIGH" }; // TODO how to use TodoItem.PriorityLevels enum instead
	 /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener mAlertPositiveListener;
 
    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    interface AlertPositiveListener {
        public void onPositiveClick(String position);
    }
    
    
    public PriorityDialogFragment(int position) {
		mSelectedPriority = position;
	}
    
    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
    */
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            mAlertPositiveListener.onPositiveClick(items[position].toString());
        }
    };
    
    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
 
        /** Setting a title for the window */
        b.setTitle("Select The Priority");
 
        /** Setting items to the alert dialog */
        b.setSingleChoiceItems( items, mSelectedPriority, null);
 
        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",positiveListener);
 
        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);
 
        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();
        
        mAlertPositiveListener = (AlertPositiveListener) getActivity();
 
        /** Return the alert dialog window */
        return d;
    }

}
