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
        //������ �����Ҷ��� ���� �� ������� layout�� inflate ��Ŵ.
        //�ǿ��� ����� �ĺ��ڸ� �����ؼ� ���� ������ �׸���
        LayoutInflater.from(this).inflate(R.layout.tab_layout, 
        		tabHost.getTabContentView(), true);
        //�ǿ� TabSpec ��ü�� ����(����ǥ(tab1����)�ϰ� , Grid��� ���� ���̰�, 
        //�ش� ���� ������ ����Ʈ�� ����.(�ش� ��Ƽ��Ƽ�� �Ǿȿ� ǥ�õ�)
        // newTabSpec("tab1")�� createTabContent���� �ǲ���ǥ�� �߿��ϰ� ���� �� ����.
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("�׸���").
        		 setContent(new Intent(this, SimpleGridView.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("��¥").
        		 setContent(new Intent(this, DateTimePickerActivity.class)));
        //���ڿ��� ID�� �̿��Ͽ� ���� ������ ���� �Ѵ�.
        //�ǿ� ���� �ĺ��ڸ� ����Ϸ��� �ݵ�� LayoutInflater�� �̿��ؾ� ��.
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Basic").
        		setContent(R.id.two_texts));

        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Factory").
        		//TabLayout(TabActivity)��ü�� ���� �������� ����
        		setContent(this));
    }
   //TabActivity�� TabHost.TabContentFactory()�� ������.(TabActivity������ 
    // Factory�� ������ ���.
    public View createTabContent(String tag) {
    	//���� ����ǥ�� ���� �Ǵ�(�ٸ� ���� �ٳ�͵� �ð��� ������ ������ Ȯ��)
        if (tag.compareTo("tab4") == 0) {
            TextView tv = new TextView(this);
            Date now = new Date();
            tv.setText("��¥ ����!  " + now.toString());
            tv.setTextSize((float) 24);
            return (tv);
        } else {
            return null;
        }
    }
}
