package com.pyo.android.widget.expension;

import android.app.Activity;
import android.os.Bundle;

public class SimpleDrawActivity extends Activity {
   @Override
   public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //���̾ƿ� �������� ����
    //setContentView(R.layout.draw_view_layout);
    
    //���� �����Ͽ� ����
    DrawView aView = new DrawView(this);
   setContentView(aView);
   }
}