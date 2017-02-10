package com.pyo.android.simple.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class DateTimePicker extends Activity {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_time_layout);
			
		 final TextView  textView =(TextView)findViewById(R.id.text_display_current_datetime);
		 final DatePicker datePicker = (DatePicker)findViewById(R.id.date_picker_widget);
		 final TimePicker timePicker = (TimePicker)findViewById(R.id.time_picker_widget);
		
		 datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 
		          new DatePicker.OnDateChangedListener() {
		    public void onDateChanged(DatePicker view, int year,
				                         int monthOfYear, int dayOfMonth) {			
			  Date date = new Date(year-1900, monthOfYear, dayOfMonth, 
					         timePicker.getCurrentHour(), timePicker.getCurrentMinute());
			  textView.setText(dateFormat.format(date));
		    }		
	       });
		 timePicker.setOnTimeChangedListener(new
	    		   TimePicker.OnTimeChangedListener(){
		    public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
			   Date date = new Date(datePicker.getYear(), datePicker.getMonth(), 
					   datePicker.getDayOfMonth(), hourOfDay, minute);
			   textView.setText(dateFormat.format(date));
		    }		
		});				
	   }
}