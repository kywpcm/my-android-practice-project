/*
 *  EditText ȯ�漳�� ��
 */
package com.pyo.preference;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class EditTextPreferenceActivity extends PreferenceActivity {
	private  EditTextPreference editTextPrefer;
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       addPreferencesFromResource(R.xml.edit_text_preference_screen);
	       
	       editTextPrefer = (EditTextPreference)this.findPreference("edit_text_input_item_key");
	
	       editTextPrefer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				//���� ��ȭ���ڰ� ������ �� ����
				editTextPrefer.getDialog().setOnDismissListener(dismissListener);
				return true;
			}
		   });
	}
	private DialogInterface.OnDismissListener dismissListener = new DialogInterface.OnDismissListener(){
	   public void onDismiss(DialogInterface dialog){ 
		    //���� ȯ�漳���Ǿ� �ִ� ���� ������ 
		    //editTextPrefer.getText();
		   
		    //����ڰ� �Է��� ���� ������
		   int inputDigitValue = 0 ;
		   try{
		      inputDigitValue = Integer.parseInt(editTextPrefer.getEditText().getText().toString());
		      //�Է°��� �������̸� ���� �Է� �Ѵ�
			  if(inputDigitValue >= 0 || inputDigitValue < 0){
			    //������ ���� ���� ������ ���� ��ȣ ������ ���� �� �� ����
			    SharedPreferences contextPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			        
			     //���� ȯ�漳���� ������ȣ�������� �������� ���
			        /*
			         * String preferenceName =  getPreferenceManager().getSharedPreferencesName();
			         * SharedPreferences contextPreference = EditTextPreferenceActivity.this.getSharedPreferences(preferenceName,0);
			         */
			        //SharedPreferences contextPreference = EditTextPreferenceActivity.this.getSharedPreferences("com.pyo.preference_preferences",0);
			        //SharedPreferences contextPreference = getPreferenceManager().getSharedPreferences();
			        	        
			        SharedPreferences.Editor editor = contextPreference.edit();
			        editor.putString(editTextPrefer.getKey(), String.valueOf(inputDigitValue));
			        editor.commit();
			        Toast.makeText(EditTextPreferenceActivity.this, "���� ������ ���� [" + 
			        		inputDigitValue + "] �Դϴ�", Toast.LENGTH_LONG).show();
			  }
		   }catch(NumberFormatException nfe){
			 //��ġ�� �ƴϸ� �ٽ� ��ȭ���ڸ� �ٿ�
		    	editTextPrefer.getDialog().show();
		    	Toast.makeText(EditTextPreferenceActivity.this,
		    			"������[ " + nfe.getMessage() + "] �� �Է��� �ּ��� ", Toast.LENGTH_LONG).show();
		   }
	   }
	};
}
