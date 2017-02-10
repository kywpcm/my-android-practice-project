package com.pyo.simple.prefs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public abstract class SimplePreferenceSuper extends Activity {
	public static final String 
	              PREFERENCE_SHARED_FILENAME = "SharedPerferenceFileName";
	public static final String 
	             PREFERENCE_ACTIVATY_NAME = "PreferenceActivityName";
	private  SharedPreferences sharedPrefs;
	private  SharedPreferences privatePrefs;
	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);	
		setContentView(R.layout.preference_layout);
             
		//��Ƽ��Ƽ�� ������ȣ������ �ִٸ� ���� �´�
         sharedPrefs = 
        	  getSharedPreferences(PREFERENCE_SHARED_FILENAME, MODE_PRIVATE);
        //���� ��Ƽ��Ƽ�� ���μ�ȣ������ ������(SimplePrefs.class)
         privatePrefs = getPreferences(MODE_PRIVATE); 

        //���� ��Ƽ��Ƽ�� �̸��� ���Ϲ޾� ȭ�鿡 ���
        final TextView currentActivityName = (TextView)findViewById(R.id.Title);
        currentActivityName.setText(this.getLocalClassName());
        
        // ���� ��ȣ ������ �̸��� Ű/�� ���� ������ 
        final TextView sharedTextView = (TextView)findViewById(R.id.CurrentPrefs);
        sharedTextView.setText(displayPreferences(sharedPrefs.getAll()));
        
        // ���� ��ȣ ������ ȭ�鿡 ��� 
        final TextView prefsActivityView = 
        	(TextView)findViewById(R.id.CurrentActivityPrefs);
        prefsActivityView.setText(displayPreferences(privatePrefs.getAll()));
        
        // �ٸ� ��Ƽ��Ƽ ����
        final Button switchBtn = (Button) findViewById(R.id.SwtichBtn);         
        switchBtn.setOnClickListener(new View.OnClickListener() {             
        	public void onClick(View v) {                 
              Intent intent = new Intent(
            		    SimplePreferenceSuper.this, getSwitchActivityClass());
   			  startActivity(intent);
        	}         
         });
        // ���� ��ȣ ���� Add/Edit button clicks
        final Button prefsAddPrivateButton = 
        	       (Button) findViewById(R.id.ButtonAddActivityPref);         
        prefsAddPrivateButton.setOnClickListener(
        		             new View.OnClickListener() {             
        	public void onClick(View v) {                 
          		final EditText prefName = 
          			(EditText)findViewById(R.id.EditTextPrefName);
        		final EditText prefValue = 
        			(EditText)findViewById(R.id.EditTextPrefValue);
        		final TextView prefs = 
        			(TextView)findViewById(R.id.CurrentActivityPrefs);
        		
        		SharedPreferences privateActivityPrefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = privateActivityPrefs.edit();          
                String strPrefName = 
                	prefName.getText().toString();
                String strPrefValue = 
                	prefValue.getText().toString();
                               
                prefEditor.putString(strPrefName, strPrefValue);
                prefEditor.commit();
                
        		prefs.setText(displayPreferences(privateActivityPrefs.getAll())); 
        	}         
        });
        //���뼱ȣ ���� Key ����
        final Button clearActivityPrefsBtn = 
        	       (Button) findViewById(R.id.ButtonClearActPrefByName);         
        clearActivityPrefsBtn.setOnClickListener(
        		             new View.OnClickListener() {             
        	public void onClick(View v) {                 
          		final EditText prefName = 
          			 (EditText)findViewById(R.id.EditTextPrefName);
        		final TextView prefs = 
        			(TextView)findViewById(R.id.CurrentActivityPrefs);
        		
                SharedPreferences privateActivityPrefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = privateActivityPrefs.edit();
                
                String strPrefName = prefName.getText().toString();
            
                if(privatePrefs.contains(strPrefName)){
                  prefEditor.remove(strPrefName);
                }
                prefEditor.commit();              
        		prefs.setText(displayPreferences(privateActivityPrefs.getAll())); 
        	}         
         });
        //���� ��ȣ ���� ��ü ����
        final Button clearActivityAllBtn = 
        	   (Button) findViewById(R.id.ButtonClearAct);         
        clearActivityAllBtn.setOnClickListener(new View.OnClickListener() {             
        	public void onClick(View v) { 
        		final TextView prefs = 
        			(TextView)findViewById(R.id.CurrentActivityPrefs);
                SharedPreferences privatePrefsAllClear = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = privatePrefsAllClear.edit();                                
              
                prefEditor.clear();
                prefEditor.commit();
                
        		prefs.setText(displayPreferences(privatePrefsAllClear.getAll())); 
        		
        		Toast.makeText(SimplePreferenceSuper.this,
        				"��Ƽ��Ƽ ��ȣ���� ��ü Key ����!",
        				    Toast.LENGTH_SHORT).show();
        		}         
        	});
        //������ȣ���� �߰�
        final Button sharedPrefsAddButton = 
        	    (Button) findViewById(R.id.ButtonAddSharedPref);         
        sharedPrefsAddButton.setOnClickListener(new View.OnClickListener() {             
        	public void onClick(View v) {                 
        		final EditText prefName = 
        			(EditText)findViewById(R.id.EditTextPrefName);
        		final EditText prefValue = 
        			(EditText)findViewById(R.id.EditTextPrefValue);
        		final TextView prefs = 
        			(TextView)findViewById(R.id.CurrentPrefs);
        		
                SharedPreferences addSharedPrefs = 
                	getSharedPreferences(PREFERENCE_SHARED_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = addSharedPrefs.edit();
                
                String strPrefName = prefName.getText().toString();
                String strPrefValue = prefValue.getText().toString();
                               
                prefEditor.putString(strPrefName, strPrefValue);
                prefEditor.commit();
                
          		prefs.setText(displayPreferences(addSharedPrefs.getAll())); 
        	}         
        });
        //���� ��ȣ ���� Key ����
        final Button clearPrefSharedByNameButton =
        	    (Button) findViewById(R.id.ButtonClearSharedPrefByName);         
        clearPrefSharedByNameButton.setOnClickListener(new View.OnClickListener() {             
        	public void onClick(View v) {                 
          		final EditText prefName = 
          			(EditText)findViewById(R.id.EditTextPrefName);
        		final TextView prefs = 
        			(TextView)findViewById(R.id.CurrentPrefs);
        	
                SharedPreferences clearSharedByName = 
                	getSharedPreferences(PREFERENCE_SHARED_FILENAME,MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = clearSharedByName.edit();
                
                String strPrefName = prefName.getText().toString();
                
                if(clearSharedByName.contains(strPrefName)){
                   	prefEditor.remove(strPrefName);
                }
               prefEditor.commit();
               prefs.setText(displayPreferences(clearSharedByName.getAll())); 
        	}         
        });
      //���� ��ȣ ���� ��� ����
       final Button clearSharedAllButton = 
    	      (Button) findViewById(R.id.ButtonClearShared);         
       clearSharedAllButton.setOnClickListener(
        		          new View.OnClickListener() {             
        	public void onClick(View v) {                 
          		final TextView prefs = 
          			  (TextView)findViewById(R.id.CurrentPrefs);
                SharedPreferences sharedAllClear = 
                	 getSharedPreferences(PREFERENCE_SHARED_FILENAME,MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = 
                	sharedAllClear.edit();
      
                prefEditor.clear();
                prefEditor.commit();
     
        		prefs.setText(displayPreferences(sharedAllClear.getAll())); 
        		Toast.makeText(SimplePreferenceSuper.this, 
        				"��ȣ ���� Ŭ����!", 
        				     Toast.LENGTH_SHORT).show();
        		}         
        	});
         //��ȣ ���� �ݹ� �̺�Ʈ!
       sharedPrefs.registerOnSharedPreferenceChangeListener(callBackPrefsListener);
	}
	private  OnSharedPreferenceChangeListener callBackPrefsListener =
		         new  OnSharedPreferenceChangeListener(){
		public void onSharedPreferenceChanged(
    			SharedPreferences sharedPreferences, String key) {
    		    Toast.makeText(SimplePreferenceSuper.this, 
    				"��ȣ ���� �ݹ� ȣ��! " + key, Toast.LENGTH_SHORT)
    				 .show();
    	}   
	};
	public abstract Class<?> getSwitchActivityClass();
	
	public  String  displayPreferences(Map<String,?>sharedPrefsMap){
		StringBuilder strBuf = null;
		Collection<String> prefsKeys = sharedPrefsMap.keySet();
		if( !prefsKeys.isEmpty()){
			strBuf = new StringBuilder();
			Iterator<String>  keys = prefsKeys.iterator();
			while(keys.hasNext()){
			   String key= keys.next();
			   Object value = sharedPrefsMap.get(key);
			   strBuf.append("[" + key + "=").append(value.toString() + "]\n");
			}
			return strBuf.toString();
		}
	    
		return "��ȣ ���� Key/Value ����";
	}
}