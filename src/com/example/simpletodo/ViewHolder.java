package com.example.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder {
	TextView textView;
	TextView itemDueDateView;
	ImageView priorityImage;
	GestureDetectorCompat mDetector;
	LinearLayout container;
	int position;
	
	
	public void highLightHolder(Context context) {
		itemDueDateView.setBackgroundColor(context
				.getResources().getColor(R.color.RSSOrange));
		textView.setBackgroundColor(context
				.getResources().getColor(R.color.RSSOrange));
		priorityImage.setBackgroundColor(context
				.getResources().getColor(R.color.RSSOrange));
	}
	
	public void setFont(Context context) {
		Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");  
		textView.setTypeface(font); 
		itemDueDateView.setTypeface(font);
	}
	
	public void unHighLightHolder() {
		itemDueDateView.setBackgroundColor(Color.WHITE);
		textView.setBackgroundColor(Color.WHITE);
		priorityImage.setBackgroundColor(Color.WHITE);
	}
	
	public void setPriorityImage(Context context, TodoItem.PriorityLevels pri) {
		switch (pri) {
		case HIGH:
				priorityImage.setImageDrawable(context.getResources().getDrawable(R.drawable.high2) );
				break;
		case MEDIUM:
			priorityImage.setImageDrawable(context.getResources().getDrawable(R.drawable.med2));
			break;
		case LOW:
			priorityImage.setImageResource(R.drawable.low2);
			break;
		}
	}

}
