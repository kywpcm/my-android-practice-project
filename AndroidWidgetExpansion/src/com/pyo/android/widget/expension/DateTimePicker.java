package com.pyo.android.widget.expension;

import java.util.Calendar;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimePicker extends LinearLayout {
	public interface OnDateTimeChangedListener {
		void onDateTimeChanged(DateTimePicker view, int year,
			                   int monthOfYear,
				int dayOfMonth, int hourOfDay, int minute);
	}

	private OnDateTimeChangedListener onDateTimeChangedListener;

	private final DatePicker datePicker;
	private final CheckBox enableTimeCheckBox;
	private final TimePicker timePicker;

	/**
	 * @param context
	 */
	public DateTimePicker(final Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DateTimePicker(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		// Layout
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.datetime_picker, this, true);

		// Attribute
		Calendar calendar = Calendar.getInstance();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.DataTimePickerStyle);
		final int _currentYear = a.getInt(R.styleable.DataTimePickerStyle_year,
				calendar.get(Calendar.YEAR));
		final int _currentMonth = a.getInt(R.styleable.DataTimePickerStyle_month,
				calendar.get(Calendar.MONTH));
		final int _currentDay = a.getInt(R.styleable.DataTimePickerStyle_day,
				calendar.get(Calendar.DAY_OF_MONTH));
		final int _currentHour = a.getInt(R.styleable.DataTimePickerStyle_hour,
				calendar.get(Calendar.HOUR_OF_DAY));
		final int _currentMinute = a.getInt(R.styleable.DataTimePickerStyle_minute,
				calendar.get(Calendar.MINUTE));

		// DatePicker
		datePicker = 
		   (DatePicker)findViewById(R.id.datetime_picker_date_picker);
		datePicker.init(_currentYear, _currentMonth, _currentDay,
		   new OnDateChangedListener() {
		     public void onDateChanged(final DatePicker view,
				final int year, final int monthOfYear,
				final int dayOfMonth) {
		        if (onDateTimeChangedListener != null) {
			  onDateTimeChangedListener.onDateTimeChanged(
				    DateTimePicker.this, year, monthOfYear,
				    dayOfMonth, timePicker.getCurrentHour(),
				    timePicker.getCurrentMinute());
			}
		     }
		});

		// Enable Time checkbox
		enableTimeCheckBox = 
		   (CheckBox) findViewById(R.id.datetime_picker_enable_time);
		enableTimeCheckBox
		  .setOnCheckedChangeListener(new OnCheckedChangeListener(){
		    public void onCheckedChanged(CompoundButton buttonView,
					      boolean isChecked) {
		      timePicker.setEnabled(isChecked);
		      timePicker.setVisibility((enableTimeCheckBox.isChecked() ? 
		                          View.VISIBLE : View.INVISIBLE));
		    }
		});

		// TimePicker
		timePicker = (TimePicker) findViewById(R.id.datetime_picker_time_picker);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
		  public void onTimeChanged(final TimePicker view,
		   final int hourOfDay, final int minute) {
		     if (onDateTimeChangedListener != null) {
		       onDateTimeChangedListener.onDateTimeChanged(
			  DateTimePicker.this, datePicker.getYear(),
			  datePicker.getMonth(), datePicker.getDayOfMonth(),
			  hourOfDay, minute);
		     }
	          }
		});
		timePicker.setCurrentHour(_currentHour);
		timePicker.setCurrentMinute(_currentMinute);
		timePicker.setEnabled(enableTimeCheckBox.isChecked());
		timePicker.setVisibility((enableTimeCheckBox.isChecked() ?
		                    View.VISIBLE : View.INVISIBLE));
	}

	public void setOnDateTimeChangedListener(
			OnDateTimeChangedListener onDateTimeChangedListener){
		this.onDateTimeChangedListener = onDateTimeChangedListener;
	}

	public void updateDateTime(int year, int monthOfYear, int dayOfMonth,
			int currentHour, int currentMinute) {
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
		timePicker.setCurrentHour(currentHour);
		timePicker.setCurrentMinute(currentMinute);
	}

	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
	}

	public void setIs24HourView(final boolean is24HourView) {
		timePicker.setIs24HourView(is24HourView);
	}

	public int getYear() {
		return datePicker.getYear();
	}

	public int getMonth() {
		return datePicker.getMonth();
	}

	public int getDayOfMonth() {
		return datePicker.getDayOfMonth();
	}

	public int getCurrentHour() {
		return timePicker.getCurrentHour();
	}

	public int getCurrentMinute() {
		return timePicker.getCurrentMinute();
	}

	public boolean enableTime() {
		return enableTimeCheckBox.isChecked();
	}
}