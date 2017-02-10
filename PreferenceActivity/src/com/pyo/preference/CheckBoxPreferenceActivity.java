/*
 *  CheckBox 환경설정
 */
package com.pyo.preference;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class CheckBoxPreferenceActivity extends PreferenceActivity{
	@Override
	 public void onCreate(Bundle savedInstanceState){
	     super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.check_box_preference_screen);

         CheckBoxPreference  checkBoxPants = (CheckBoxPreference)findPreference("check_box_pants");
         CheckBoxPreference  checkBoxJacket = (CheckBoxPreference)findPreference("check_box_jacket");
         CheckBoxPreference  checkBoxShirt = (CheckBoxPreference)findPreference("check_box_shirt");
         
         checkBoxPants.setOnPreferenceClickListener(preferenceClick);
         checkBoxJacket.setOnPreferenceClickListener(preferenceClick);
         checkBoxShirt.setOnPreferenceClickListener(preferenceClick);
	}
	private  OnPreferenceClickListener preferenceClick = new OnPreferenceClickListener(){
	    @Override
	    public boolean onPreferenceClick(Preference preference){
	    	
	    	CheckBoxPreference checkPreference = (CheckBoxPreference)preference;
	    	String checkBoxTitle = (String)checkPreference.getTitle();
	    	String checkBoxKey = preference.getKey();
	    	boolean checkBoxFlag = false;
	    	if(checkBoxKey.equals("check_box_pants")){
	    		checkBoxFlag = checkPreference.isChecked() ;
	    	}else if(checkBoxKey.equals("check_box_jacket")){
	    		checkBoxFlag = checkPreference.isChecked() ;
	    	}else if(checkBoxKey.equals("check_box_shirt")){
	    		checkBoxFlag = checkPreference.isChecked() ;
	    	}
	    	Toast.makeText(CheckBoxPreferenceActivity.this, checkBoxTitle +
	    			           "의 체크 상태는 " + checkBoxFlag + " 입니다.", Toast.LENGTH_SHORT).show();
	    	return true;
	    }
	};
}
