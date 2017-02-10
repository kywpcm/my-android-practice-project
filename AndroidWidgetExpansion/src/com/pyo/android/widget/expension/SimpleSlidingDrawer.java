package com.pyo.android.widget.expension;

import android.app.Activity;
import android.os.Bundle;

public class SimpleSlidingDrawer extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //이 레이아웃 xml은 꼭 확인 하기 바람.
        setContentView(R.layout.drawer_layout);
    }
}
