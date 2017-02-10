package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class AutoCompleteTextViewActivity extends Activity {
	   private Spinner bloodGroup;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
		 
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.auto_complete_layout);
	     
	     bloodGroup = (Spinner)findViewById(R.id.spinner_id);
	     String arrayBloodType [] = getResources().getStringArray(R.array.blood_group);
	     final ArrayAdapter<String> spinnerAdapter =
             new ArrayAdapter<String>(
                 this,
                 android.R.layout.simple_dropdown_item_1line,
                 arrayBloodType);
	     //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	     bloodGroup.setAdapter(spinnerAdapter);
	     
	     bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent,View view, int position,long id){
		       //View selectedView = bloodGroup.getSelectedView();
		       Toast.makeText(getApplicationContext(), 
		    		                  spinnerAdapter.getItem(position) + " 선택됨!", 
		    		                  Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent){
		    	Toast.makeText(getApplicationContext(), 
		                  " 혈액형이 선택 되지 않았네요!", 
		                  Toast.LENGTH_SHORT).show();
		    }
	     });
	     //자동 완성 제시어 목록
	     final String[] autoColors =
	            {   "red", "green", "orange", "blue", "purple",
	                "black", "yellow", "cyan", "magenta","white" };
	     //어댑터에 제시어 목록을 출력할 레이아웃 설정
	     ArrayAdapter<String> adapter =
	                 new ArrayAdapter<String>(
	                     this,
	                     android.R.layout.simple_dropdown_item_1line,
	                     autoColors);
	     AutoCompleteTextView autoEdit =
	           (AutoCompleteTextView)findViewById(R.id.auto_complete_text_view);
	     //위젯과 어댑터 연결
	     autoEdit.setAdapter(adapter);
	        
	     MultiAutoCompleteTextView multiAutoEdit =
	               (MultiAutoCompleteTextView) 
	                     findViewById(R.id.multi_auto_complete_text_view);
	     multiAutoEdit.setAdapter(adapter);
	     //제시어 목록을 구분할 구분자(콤마) 설정
	     multiAutoEdit.setTokenizer(
	      		   new MultiAutoCompleteTextView.CommaTokenizer());
	   }
}