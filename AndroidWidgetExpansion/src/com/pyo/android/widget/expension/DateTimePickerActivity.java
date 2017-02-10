package com.pyo.android.widget.expension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DateTimePickerActivity extends Activity {

  final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
  TextView timeView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.date_time_picker_layout);

   timeView = (TextView) 
	findViewById(R.id.datetime_picker_activity_text_view);
   final DateTimePicker dateTimePicker =
      (DateTimePicker)findViewById(R.id.datetime_picker_activity_datetime_picker);
   /*dateTimePicker.setOnDateTimeChangedListener(new 
		      DateTimePicker.OnDateTimeChangedListener(){
      public void onDateTimeChanged(DateTimePicker view,
			int year, int monthOfYear, int dayOfMonth,
			int hourOfDay, int minute) {
	      Calendar calendar = Calendar.getInstance();
	      calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
	      timeView.setText(dateFormat.format(calendar.getTime()));
      }
   });*/
   dateTimePicker.setOnDateTimeChangedListener(new DateTimePickerImpl());
   Calendar calendar = Calendar.getInstance();
   calendar.set(dateTimePicker.getYear(), 
             dateTimePicker.getMonth(),
	     dateTimePicker.getDayOfMonth(),
	     dateTimePicker.getCurrentHour(),
	     dateTimePicker.getCurrentMinute());
   timeView.setText(dateFormat.format(calendar.getTime()));
  }
  private class  DateTimePickerImpl implements DateTimePicker.OnDateTimeChangedListener{
	  public void onDateTimeChanged(DateTimePicker view,
				int year, int monthOfYear, int dayOfMonth,
				int hourOfDay, int minute) {
		      Calendar calendar = Calendar.getInstance();
		      calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
		      timeView.setText(dateFormat.format(calendar.getTime()));
	      }
  }
}