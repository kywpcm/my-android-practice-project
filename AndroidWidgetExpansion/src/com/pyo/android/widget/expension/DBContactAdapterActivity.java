package com.pyo.android.widget.expension;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DBContactAdapterActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 String[] requestedcolumns = {
				//contacts.phones._id,
				ContactsContract.Contacts._ID,
				//contacts.phones.name,
				ContactsContract.Contacts.DISPLAY_NAME,
				//contacts.phones.number,
				ContactsContract.Contacts.HAS_PHONE_NUMBER
		};
		Cursor names = managedQuery(
				//contacts.phones.content_uri,
				ContactsContract.Contacts.CONTENT_URI,
				requestedcolumns, null, null, null);
		startManagingCursor(names);
		setContentView(R.layout.contact_layout);
		ListAdapter adapter = new SimpleCursorAdapter(
				this, R.layout.contact_item_simple,
				names, new String[] {
					//Contacts.Phones.NAME
						ContactsContract.Contacts.DISPLAY_NAME
				}, new int[] {
					R.id.contact_item_simple_text
				});
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v,
			int position, long id) {

		Cursor phone = (Cursor) l.getItemAtPosition(position);

		TextView tv = ((TextView) v);
		String name = phone.getString(phone
				//.getColumnIndex(Contacts.Phones.NAME));
				.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		String num = phone
				.getString(phone
						//.getColumnIndex(Contacts.Phones.NUMBER));
						.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

		String displayed = tv.getText().toString();
		if (displayed.compareTo(name) == 0) {
			tv.setText(num);
		} else {
			tv.setText(name);
		}
	}
}