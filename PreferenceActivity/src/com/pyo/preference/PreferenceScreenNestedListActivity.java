package com.pyo.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceScreenNestedListActivity extends PreferenceActivity {
   @Override
	 public void onCreate(Bundle savedInstanceState){
	     super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.perference_screen_nested);
     }
}
