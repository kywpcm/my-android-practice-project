package com.pyo.simple.prefs;

import android.content.SharedPreferences;
import android.os.Bundle;

//두번째 액티비티
public class SimplePreferenceActivity2 extends SimplePreferenceSuper {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
 	  SharedPreferences privatePrefs = 
 		    getPreferences(MODE_PRIVATE); 
 	  if(privatePrefs.contains(PREFERENCE_ACTIVATY_NAME) == false){
	     SharedPreferences.Editor prefEditor = 
	    	        privatePrefs.edit();
	     prefEditor.putString(PREFERENCE_ACTIVATY_NAME, 
	    		    this.getLocalClassName()); 
	     prefEditor.putLong("LongValue", java.lang.Long.MAX_VALUE);
	     prefEditor.commit();
	  }
	  super.onCreate(savedInstanceState); 
 }
	@Override
	public Class<?> getSwitchActivityClass() {
		return SimplePreferenceActivity1.class;
	}
}