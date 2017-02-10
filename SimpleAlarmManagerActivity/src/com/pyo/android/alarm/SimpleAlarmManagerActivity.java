package com.pyo.android.alarm;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class SimpleAlarmManagerActivity extends Activity
implements OnDateChangedListener, OnTimeChangedListener{
	private AlarmManager alarmManager;
	private GregorianCalendar alarmedDate;
	private DatePicker alarmDate;
	private TimePicker alarmTime;
	private static  int FLAG = 0 ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_layout);

		FLAG++;

		alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmedDate = new GregorianCalendar();
		setContentView(R.layout.alarm_layout);

		if( FLAG > 1 ){
			Toast.makeText(this, " 알람 실행 됨! ", Toast.LENGTH_LONG).show();
		}
		Button setButton = (Button)findViewById(R.id.alarmSet);
		setButton.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				settingAlarm();
			}
		});

		Button resetButton = (Button)findViewById(R.id.alarmRest);
		resetButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetAlarm();
			}
		});
		alarmDate = (DatePicker)findViewById(R.id.date_picker);
		alarmDate.init(
				alarmedDate.get(Calendar.YEAR),
				alarmedDate.get(Calendar.MONTH),
				alarmedDate.get(Calendar.DAY_OF_MONTH),this);
		alarmTime = (TimePicker)findViewById(R.id.time_picker);
		alarmTime.setCurrentHour(alarmedDate.get(Calendar.HOUR_OF_DAY));
		alarmTime.setCurrentMinute(alarmedDate.get(Calendar.MINUTE));
		alarmTime.setOnTimeChangedListener(this);
	}
	//알람 설정
	private void settingAlarm(){
		alarmManager.set(AlarmManager.RTC_WAKEUP,
				alarmedDate.getTimeInMillis(),
				getPendingIntent());
	}
	//알람 해제
	private void resetAlarm(){
		alarmManager.cancel(getPendingIntent());
	}
	//알람 설정 시간에 해당 할 인텐트 설정
	private PendingIntent getPendingIntent(){
		Intent intent = new Intent(getApplicationContext(),
				SimpleAlarmManagerActivity.class);
		PendingIntent pIntent = 
				PendingIntent.getActivity(this, 0, intent, 0);
		return pIntent;
	}
	//일정에 대한 리스너
	public  void onDateChanged(DatePicker view, int year,int monthOfYear,
			int dayOfMonth){
		alarmedDate.set(year,monthOfYear,dayOfMonth,
				alarmTime.getCurrentHour(),
				alarmTime.getCurrentMinute());
		Log.i("AlarmTest_onDateChanged",alarmedDate.getTime().toString());
	}
	//시각 설정
	public void onTimeChanged(TimePicker view,int hourOfDay, int minute){
		alarmedDate.set(alarmDate.getYear(),alarmDate.getMonth(),
				alarmDate.getDayOfMonth(),hourOfDay,minute);
		Log.i("AlarmTest_onDateChanged",alarmedDate.getTime().toString());
	}
}