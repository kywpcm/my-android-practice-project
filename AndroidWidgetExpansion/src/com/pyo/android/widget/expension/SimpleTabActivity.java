package com.pyo.android.widget.expension;

import java.util.Date;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class SimpleTabActivity extends TabActivity implements 
                    android.widget.TabHost.TabContentFactory{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();      
        //탭으로 구성할때는 보통 이 방법으로 layout을 inflate 시킴.
        //탭에서 뷰들의 식별자를 참조해서 탭의 내용을 그릴때
        LayoutInflater.from(this).inflate(R.layout.tab_layout, 
        		tabHost.getTabContentView(), true);
        //탭에 TabSpec 객체의 생성(꼬리표(tab1부착)하고 , Grid라는 라벨을 붙이고, 
        //해당 탭의 내용은 인텐트로 구성.(해당 액티비티가 탭안에 표시됨)
        // newTabSpec("tab1")은 createTabContent에서 탭꼬리표가 중요하게 사용될 수 있음.
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("그리드").
        		 setContent(new Intent(this, SimpleGridView.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("날짜").
        		 setContent(new Intent(this, DateTimePickerActivity.class)));
        //뷰자원의 ID를 이용하여 탭의 내용을 구성 한다.
        //탭에 뷰의 식별자를 사용하려면 반드시 LayoutInflater를 이용해야 함.
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Basic").
        		setContent(R.id.two_texts));

        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Factory").
        		//TabLayout(TabActivity)자체를 탭의 내용으로 지정
        		setContent(this));
    }
   //TabActivity는 TabHost.TabContentFactory()를 구현함.(TabActivity구현시 
    // Factory의 역할을 담당.
    public View createTabContent(String tag) {
    	//탭의 꼬리표를 보고 판단(다른 탭을 다녀와도 시간은 변하지 않음을 확인)
        if (tag.compareTo("tab4") == 0) {
            TextView tv = new TextView(this);
            Date now = new Date();
            tv.setText("날짜 생성!  " + now.toString());
            tv.setTextSize((float) 24);
            return (tv);
        } else {
            return null;
        }
    }
}
