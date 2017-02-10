package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

public class TextInputActivity extends Activity {
	
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {	 
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.text_input_layout);
	      
	    final EditText textFiltered =
	          (EditText) findViewById(R.id.input_filtered);
	      textFiltered.setFilters(new InputFilter[]
	                         {   new InputFilter.AllCaps(),
	                             new InputFilter.LengthFilter(2) 
	                         });    
	    }
}
