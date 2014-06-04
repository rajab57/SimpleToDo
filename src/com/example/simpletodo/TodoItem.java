package com.example.simpletodo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoItem implements Serializable {

	private static final AtomicInteger NEXT_ID = new AtomicInteger(101);
	private static final long serialVersionUID = -8507171562852085782L;
	private String mItemName = "";
	private PriorityLevels mPriority = PriorityLevels.LOW;
	private String mDueDate = "None"; // Format yyyy-mm-dd
	private transient long mDueDateInEpoch = 0; // do not access this variable directly ( hack TODO)
	private long mReminder = -1;
	private long mTimestamp; // in epoch

	public static enum PriorityLevels {
		LOW, MEDIUM, HIGH
	};

	private int mId = -1; // unique ID for the alarm, only one alarm can be set
							// for each item.

	public TodoItem() {}
	
	public TodoItem(String name) {
		setItemName(name);
		setTimestamp();
		// default priority is low
		// set default duedate to one year from now
		setDueDate(getNextYear());
		setId();

	}
	
	public void setId() {
		NEXT_ID.getAndIncrement();
		mId = NEXT_ID.get();
	}

	public int getId() {
		return mId ;
	}

	public static String getNextYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = cal.getTime();
		return formatDate(nextYear);

	}

	public static String formatDate(Date dt) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reportDate = df.format(dt);
		return reportDate;

	}
	
	public static String formatDate(long epoch) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reportDate = df.format(new Date(epoch));
		return reportDate;

	}
	
	public static String formatDateTime(long epoch) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String reportDate = df.format(new Date(epoch));
		return reportDate;

	}
	public static String formatDate(int year, int month, int day ) {
		String reportDate = String.format("%s-%s-%s" , year, month, day);
		return reportDate;

	}

	public static String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		return formatDate(today);

	}

	public static String getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = cal.getTime();
		String tom = formatDate(tomorrow);
		return tom;

	}

	public void setItemName(String name) {
		mItemName = name;
	}

	public String getItemName() {
		return mItemName;
	}

	public void setPriority(PriorityLevels priority) {
		mPriority = priority;
	}

	public PriorityLevels getPriority() {
		return mPriority;
	}

	public void setDueDate(String duedate) {
		mDueDate = duedate;
		mDueDateInEpoch = convertToEpoch(duedate);
	}

	public String getDueDate() {
		return mDueDate;
	}

	/**
	 * Give input time in epoch
	 * 
	 * @param milliseconds
	 */
	public void setReminder(long milliseconds) {
		mReminder = milliseconds;
	}

	public long getReminder() {
		return mReminder;
	}

	public void setTimestamp() {
		mTimestamp = System.currentTimeMillis() / 1000;
	}

	public long getTimestamp() {
		return mTimestamp;
	}

	private long convertToEpoch(String dt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long epoch = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(dt));
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
			epoch = cal.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epoch;

	}

	public long getDueDateInEpoch() {
		// since it is transient , it is not persistent, need a better design
		if ( mDueDateInEpoch < 1 ) 
			mDueDateInEpoch = convertToEpoch(mDueDate);
		return mDueDateInEpoch;
	}

	public String getDueDateToDisplay() {
		if (mDueDate.equals("None"))
			return "";
		String[] parts = mDueDate.split("-");
		String displayStr = parts[1] + "/" + parts[2];
		// TODO Check for past due date
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND,0);
		today.set(Calendar.MILLISECOND,0);
		long todaytime = today.getTimeInMillis();
		
		if (getDueDateInEpoch() < todaytime )
			displayStr = TodoItemsList.DisplayDate.PastDue.name();
		else if (mDueDate.equals(TodoItem.getTodayDate()))
			displayStr = TodoItemsList.DisplayDate.Today.name();
		else if (mDueDate.equals(TodoItem.getTomorrowDate()))
			displayStr = TodoItemsList.DisplayDate.Tomorrow.name();
		return displayStr;

	}
	
	public String toString() {
		StringBuffer itemStr = new StringBuffer();
		itemStr.append(getItemName() + ",");
		itemStr.append(getPriority() + ",");
		itemStr.append(getDueDate() + ",");
		itemStr.append(getReminder() + ",");
		itemStr.append(getTimestamp() +",");
		itemStr.append(getId() +",");
		return itemStr.toString();
 	  
		
	}

}
