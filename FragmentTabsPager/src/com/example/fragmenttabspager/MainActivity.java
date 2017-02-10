package com.example.fragmenttabspager;

import com.example.fragmenttabspager.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity {

	TabHost mTabHost;
	ViewPager mPager;
//	TabManager mTabManager;
	TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mPager = (ViewPager)findViewById(R.id.realtabcontent);

		mTabHost.setup();

		mTabsAdapter = new TabsAdapter(this, mTabHost, mPager);
		mTabsAdapter.addTab(mTabHost.newTabSpec("tab1").setIndicator("one"),
				FragmentOne.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("tab2").setIndicator("two"),
				FragmentTwo.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

		//        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
		//
		//        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("one"),
		//                FragmentOne.class, null);
		//        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("two"),
		//                FragmentTwo.class, null);
		//
		//        mTabManager.setOnTabChangedListener(new OnTabChangeListener() {
		//			
		//			@Override
		//			public void onTabChanged(String tabId) {
		//				// TODO Auto-generated method stub
		//				if (tabId.equals("tab1")) {
		//					
		//				} else if (tabId.equals("tab2")) {
		//					
		//				} else if (tabId.equals("tab3")) {
		//					
		//				}
		//			}
		//		});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
