package com.pyo.android.simple.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyPadControlActivity extends Activity{
	  private InputMethodManager keyPadManager;
	   @Override
	   public void onCreate(Bundle bundle){
		   super.onCreate(bundle);
		   /*
		    *  AndroidMenifest.xml의 <activity android:windowSoftInputMode="adjustResize"과 같음
		    *  android:windowSoftInputMode="adjustPan" 는 디폴트
		    */
		  //액티비티의 사이즈에 줄여서라도 전체 화면이 보여지게 됨
		   getWindow().setSoftInputMode(
				   WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		   setContentView(R.layout.ime_key_board_control_layout);
		   //IMF Manager 서비스 객체를 얻는다
		   keyPadManager = 
			    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		   final Button keyPadShow = (Button)findViewById(R.id.key_pad_show);
		   final Button keyPadHide = (Button)findViewById(R.id.key_pad_hide);
		   final EditText keyPadControl = (EditText)findViewById(R.id.key_pad_control_edit);
		   keyPadShow.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				// 키패드 보이기
				keyPadManager.
				  showSoftInput(keyPadControl, InputMethodManager.RESULT_UNCHANGED_SHOWN);
			 }
	       });
		   keyPadHide.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					// 키패드 숨기기
					keyPadManager.hideSoftInputFromWindow(
							keyPadControl.getWindowToken(),
							InputMethodManager.RESULT_UNCHANGED_SHOWN);
				 }
		   });
	   }
}