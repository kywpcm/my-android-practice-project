package com.example.additionalmenutest;

import com.example.additionalmenutest.R;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int MENU_ID_ONE = Menu.FIRST;
	private static final int MENU_ID_TWO = Menu.FIRST + 1;
	private static final int MENU_ID_THREE = Menu.FIRST + 2;

	private TextView messageView;
	private ImageView imageView;
	private ActionBar mActionBar;

	String[] menus = { "menu1" , "menu2" , "menu3" };

	ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		messageView = (TextView)findViewById(R.id.message);
		imageView = (ImageView)findViewById(R.id.imageView1);
		EditText et = new EditText(this);
		
		registerForContextMenu(messageView);
		registerForContextMenu(imageView);

		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);  //����Ʈ ��� �׼ǹ� ������̼� set

		mAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, menus);  //���ǳʸ� ��������ִ� �����
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		//������ ���
		mActionBar.setListNavigationCallbacks(mAdapter, new OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(MainActivity.this, "menu : " + menus[itemPosition], Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem menuItem = menu.findItem(R.id.search_menu);
		SearchView searchView = (SearchView)menuItem.getActionView();
		
		if (searchView == null) {
			searchView = new SearchView(this);
			menuItem.setActionView(searchView);
		}
		
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(MainActivity.this, "query : " + query, Toast.LENGTH_SHORT).show();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.activity_start :
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
			startActivity(i);
			return true;
		case R.id.action_settings :
			Toast.makeText(this, "Settings Item Click", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item1 :
			return true;
		case R.id.item2 :
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, MENU_ID_ONE, 0, "Context One");
		switch(v.getId()) {
		case R.id.message :
			menu.add(Menu.NONE, MENU_ID_TWO, 0, "Context Two");
			break;
		case R.id.imageView1 :
			menu.add(Menu.NONE, MENU_ID_THREE, 0, "Context Three");
			break;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case MENU_ID_ONE :
			Toast.makeText(this, "Context Menu One Click", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_ID_TWO :
			Toast.makeText(this, "Context Menu Two Click", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_ID_THREE :
			Toast.makeText(this, "Context Menu Three Click", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onContextItemSelected(item);
	}

}
