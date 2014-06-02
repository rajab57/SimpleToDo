package com.example.simpletodo;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

/**
 * @author Rajalakshmi Balakrishnan
 * @version 1.0 Main entry to the program Displays the List of Todo Items and
 *          allows you to add and remove items from the list.
 * 
 */
public class TodoActivity extends Activity {
	
	private static final String TAG = "TodoActivity";

	TodoListRowAdapter itemsAdapter;
	ListView lvItems;
	FileHandlerInterface fileHandler;
	private final int REQUEST_CODE = 1;
	AlertDialog priorityDialog;	
	Button sortDueDateButton;
	Button sortPriorityButton;
	Button sortLastAddedButton;
	PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_todo);

		lvItems = (ListView) findViewById(R.id.lvItems);
		sortDueDateButton = (Button) findViewById(R.id.sortDueDateButton);
		sortPriorityButton = (Button) findViewById(R.id.sortPriorityButton);
		sortLastAddedButton = (Button) findViewById(R.id.sortLastAddedButton);

		// file setup
		fileHandler = new ComplexFileHandlerSerializable();
		File filesDir = getFilesDir(); // TODO why does this not work in the
										// FileHandler class
		
		// Item adapter setup
		itemsAdapter = new TodoListRowAdapter(TodoActivity.this, fileHandler,filesDir);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
    }
      
	

	public ListView getListView() {
		return lvItems;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_options, menu);
		return true;
	}

	/**
	 * ACTION BAR ACTIONS for each Item on the todo list
	 * 1. Set Due Date
	 * 2. Set Priority
	 * 3. Set Reminder
	 * 4. Remove Item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_setduedate) {
			viewCalendar();
			return true;
		} else if (id == R.id.action_setPriority) {
			viewPriority();
		} else if (id == R.id.action_setReminder) {
			viewAlarm();
		} else if (id == R.id.action_deleteItem) {
			deleteItem();
		}
		return super.onOptionsItemSelected(item);
	}

	public void deleteItem() {
		
		// cancel any alarms set
		Intent intent = new Intent(this, AlarmActivity.class);
		int alarmId = itemsAdapter.getSelectedItem().getId();
		Log.d(TAG,  "Cancelling Alarm with ID " + alarmId); 
		PendingIntent sender = PendingIntent.getBroadcast(this, alarmId, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.cancel(sender);
		
		itemsAdapter.remove(itemsAdapter.getSelectedPosition());
		itemsAdapter.unsetSelectedPosition();
		getActionBar().hide();
	}

	public void viewCalendar() {
		DialogFragment dialogFragment = new StartDatePicker();
		dialogFragment.show(getFragmentManager(), "dialog");

	}

	class StartDatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		int startYear;
		int startMonth;
		int startDay;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar c = Calendar.getInstance();
			startYear = c.get(Calendar.YEAR);
			startMonth = c.get(Calendar.MONTH);
			startDay = c.get(Calendar.DAY_OF_MONTH);
			// Use the current date as the default date in the picker
			DatePickerDialog dialog = new DatePickerDialog(TodoActivity.this,
					this, startYear, startMonth, startDay);
			return dialog;
		}

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// Note: month is 0 based.
			itemsAdapter.setDueDate(year, monthOfYear + 1, dayOfMonth);
		}
	}

	public void viewPriority() {
		// final AlertDialog priorityDialog = null;
		final CharSequence[] items = { "LOW", "MEDIUM", "HIGH" };
		// Get the previously selected priority level
		int prevPriority = itemsAdapter.getSelectedItem().getPriority()
				.ordinal();
		// Creating and Building the Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select The Priority");
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				priorityDialog.dismiss();
			}
		});
		builder.setSingleChoiceItems(items, prevPriority,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						itemsAdapter.setPriority(items[item].toString());

					}
				});
		priorityDialog = builder.create();
		priorityDialog.show();
	}

	
	// Bug fix  Crash - Crash  Unable to add window -- token null is not for an application
	// Replace Dialog dialog = new Dialog(getApplicationContext()); with Dialog dialog = new Dialog(this); fixes the above bug.
	// Another bug fix the onclick event on the OK button of the dialog cannot be on the XML. It will not find the method because
	// of the way it is inflated. Reference http://stackoverflow.com/questions/23848285/why-can-i-use-onclick-in-an-activity-feed-but-not-in-a-dialog-fragment
	// TODO How to define the button in XML for dialog , but add the onClickListener in the activity class
	// TODO Alarm min Date and time to be now.
	
	public void viewAlarm() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.date_time_picker);
		dialog.setTitle("Set Alarm");
		Button saveDate = (Button)dialog.findViewById(R.id.btnSave);
		saveDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				setAlarm(dialog);
			}
		});
		dialog.show();
	}
	
	public void setAlarm(Dialog dialog) {
		DatePicker dp = (DatePicker)dialog.findViewById(R.id.datePicker1);
		TimePicker tp = (TimePicker)dialog.findViewById(R.id.timePicker1);
		
		// Set the Alarm
		
		//Time when the alarm will go off.
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone("PST"));
        cal.set(dp.getYear(),dp.getMonth(), dp.getDayOfMonth(),tp.getCurrentHour(),tp.getCurrentMinute());
        //cal.add(Calendar.SECOND, 5);
        
     // save the alarm time in the DB
        itemsAdapter.setReminder(cal.getTimeInMillis());
        
        // Unique ID for each Item. This cancels all previous alarms associated 
        // with the item. Note: One alarm for each item.
        
        Log.d(TAG, "Alarm set for " + cal.getTimeInMillis());
        Log.d(TAG, "System time " + System.currentTimeMillis());
        int alarmId = itemsAdapter.getSelectedItem().getId();
        Log.d(TAG,  "Setting Alarm with ID " + alarmId);
        String task = itemsAdapter.getSelectedItem().getItemName();

       //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("task" ,task);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = 
            (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
		
	}
		
	public void addTodoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");

	}


	//
	// SORT
	//
	public void sortByPriority(View v) {
		if ( v == sortPriorityButton) {
			v.setBackgroundColor(getResources().getColor(R.color.apptheme_color) );
		}
		sortDueDateButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		sortLastAddedButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		itemsAdapter.sortByPriority();
	}

	public void sortByDueDate(View v) {
		if ( v == sortDueDateButton) {
			v.setBackgroundColor(getResources().getColor(R.color.apptheme_color) );
		}
		sortPriorityButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		sortLastAddedButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		itemsAdapter.sortByDueDate();
	}

	public void sortByLastAdded(View v) {
		if ( v == sortLastAddedButton) {
			v.setBackgroundColor(getResources().getColor(R.color.apptheme_color) );
		}
		sortPriorityButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		sortDueDateButton.setBackgroundColor(getResources().getColor(R.color.ShadowsGrey));
		itemsAdapter.sortByLastAdded();
	}

	/**
	 *  Open Edit options on long click
	 *  open detail view activity on click
	 */

	private void setupListViewListener() {
		
		// OnItemLongClick - show edit options
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {

				itemsAdapter.setSelectedPosition(position);
				if (itemsAdapter.getSelectedPosition() == -1)
					getActionBar().hide();
				else {
					getActionBar().setDisplayShowHomeEnabled(false);
					getActionBar().show();
				}
					
				return true;

			}
		});
		// OnItemClick - Open Detail Screen
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				System.out.println("OnclickEvent");
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("position", position);
				TodoItem item = (TodoItem) parent.getItemAtPosition(position);
				i.putExtra("item", item);
				startActivityForResult(i, REQUEST_CODE);

			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			String name = data.getExtras().getString("name");
			int position = data.getExtras().getInt("position");
			itemsAdapter.set(position, name);

		}
	}

	
	// SWIPE - Currently not used.

//	/**
//	 * Handle touch events to fade/move dragged items as they are swiped out
//	 */
//	boolean mSwiping = false;
//	boolean mItemPressed = false;
//	private static final int SWIPE_DURATION = 250;
//	private static final int MOVE_DURATION = 150;
//	private SwipeDetector swipeDetector;
//	public static enum Action {
//		LR, // Left to Right
//		RL, // Right to Left
//		TB, // Top to bottom
//		BT, // Bottom to Top
//		None // when no action was detected
//	}
//	
//	private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
//
//		float mDownX;
//		private int mSwipeSlop = -1;
//
//		@Override
//		public boolean onTouch(final View v, MotionEvent event) {
//			if (mSwipeSlop < 0) {
//				mSwipeSlop = ViewConfiguration.get(TodoActivity.this)
//						.getScaledTouchSlop();
//			}
//			System.out.println("swipelop " + mSwipeSlop);
//			System.out.println(event.getAction());
//			switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				System.out.println("ActionDown");
//				if (mItemPressed) {
//					// Multi-item swipes not handled
//					System.out.println(" Multi item Swipe not handled");
//					return false;
//				}
//				mItemPressed = true;
//				mDownX = event.getX();
//				break;
//			case MotionEvent.ACTION_CANCEL:
//				System.out.println(" Action cancelled");
//				mItemPressed = false;
//				break;
////			case MotionEvent.ACTION_MOVE: {
////				System.out.println("action move");
////				float x = event.getX() + v.getTranslationX();
////				float deltaX = x - mDownX;
////				float deltaXAbs = Math.abs(deltaX);
////				if (!mSwiping) {
////					System.out.println("compare with swipeslop " + deltaXAbs);
////					if (deltaXAbs > mSwipeSlop) {
////						mSwiping = true;
////						lvItems.requestDisallowInterceptTouchEvent(true);
////						// mBackgroundContainer.showBackground(v.getTop(),
////						// v.getHeight());
////					}
////				}
////			}
////				break;
//			case MotionEvent.ACTION_UP: {
//				// User let go - figure out whether to animate the view out, or
//				// back into place
//	
//				if (mSwiping) {
//					float x = event.getX() + v.getTranslationX();
//					float deltaX = x - mDownX;
//					float deltaXAbs = Math.abs(deltaX);
//					float fractionCovered;
//					float endX;
//					float endAlpha;
//					final boolean remove;
//					System.out.println(deltaX);
//					if (deltaXAbs > v.getWidth() / 4) {
//						fractionCovered = deltaXAbs / v.getWidth();
//                        endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
//                        endAlpha = 0;
//						// Greater than a quarter of the width - animate it out
//						if (deltaX < 0) {
//							Log.i(TAG, "Swipe Right to Left");
//							remove = true;
//						}
//						else if (deltaX > 0) {
//							Log.i(TAG, "Swipe Left to Right");
//							remove = false;
//						} else
//							remove = true;
//						
//						
//					} else {
//						// Not far enough - animate it back
//						fractionCovered = 1 - (deltaXAbs / v.getWidth());
//						endX = 0;
//						endAlpha = 1;
//						remove = false;
//					}
//					// Animate position and alpha of swiped item
//					// NOTE: This is a simplified version of swipe behavior, for
//					// the
//					// purposes of this demo about animation. A real version
//					// should use
//					// velocity (via the VelocityTracker class) to send the item
//					// off or
//					// back at an appropriate speed.
//					long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
//					lvItems.setEnabled(false);
//					v.animate().setDuration(duration).alpha(endAlpha)
//							.translationX(endX).withEndAction(new Runnable() {
//								@Override
//								public void run() {
//									// Restore animated values
//									v.setAlpha(1);
//									v.setTranslationX(0);
//									if (remove) {
//										animateRemoval(lvItems, v);
//									} else {
//										// mBackgroundContainer.hideBackground();
//										mSwiping = false;
//										lvItems.setEnabled(true);
//									}
//								}
//							});
//				}
//			}
//				mItemPressed = false;
//				break;
//
//			default:
//				return false;
//			}
//			return true;
//		}
//	};
//	
//
//	/**
//	 * This method animates all other views in the ListView container (not
//	 * including ignoreView) into their final positions. It is called after
//	 * ignoreView has been removed from the adapter, but before layout has been
//	 * run. The approach here is to figure out where everything is now, then
//	 * allow layout to run, then figure out where everything is after layout,
//	 * and then to run animations between all of those start/end positions.
//	 */
//	private void animateRemoval(final ListView listview, View viewToRemove) {
//		int firstVisiblePosition = listview.getFirstVisiblePosition();
//		for (int i = 0; i < listview.getChildCount(); ++i) {
//			View child = listview.getChildAt(i);
//			if (child != viewToRemove) {
//				int position = firstVisiblePosition + i;
//				long itemId = itemsAdapter.getItemId(position);
//				System.out.println("do not remove item");
//			}
//		}
//		// Delete the item from the adapter
//		int position = lvItems.getPositionForView(viewToRemove);
//		System.out.println("Remove the item");
//		itemsAdapter.remove(position);
//
//	}

}
