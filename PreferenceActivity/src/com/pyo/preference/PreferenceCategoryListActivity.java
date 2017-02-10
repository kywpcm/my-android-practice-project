package com.pyo.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceCategoryListActivity extends PreferenceActivity {
	@Override
	 public void onCreate(Bundle savedInstanceState){
	     super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.preference_screen_category_using_screen);
    }
}
