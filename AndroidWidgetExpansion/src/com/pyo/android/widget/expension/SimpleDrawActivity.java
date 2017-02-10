package com.pyo.android.widget.expension;

import android.app.Activity;
import android.os.Bundle;

public class SimpleDrawActivity extends Activity {
   @Override
   public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //레이아웃 선언으로 적용
    //setContentView(R.layout.draw_view_layout);
    
    //직접 생성하여 적용
    DrawView aView = new DrawView(this);
   setContentView(aView);
   }
}