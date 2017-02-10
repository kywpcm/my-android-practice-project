package com.pyo.android.simple.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;

public class QuickContactBadgeActivity extends Activity {
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.badge_layout);

        QuickContactBadge badgeSmall =
        	(QuickContactBadge) findViewById(R.id.badge_small);
        badgeSmall.assignContactFromEmail("insoo.pyo@gmail.com", true);
        badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL);

        QuickContactBadge badgeMedium =
        	  (QuickContactBadge) findViewById(R.id.badge_medium);
        badgeMedium.assignContactFromPhone("02-3456-7890", true);
        badgeMedium.setMode(ContactsContract.QuickContact.MODE_LARGE);
        badgeMedium.setImageResource(R.drawable.btn_zoom_page_press);
    }
    public void onPickContact(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                                                         ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case CONTACT_PICKER_RESULT:
                Uri contactUri = data.getData();
                FrameLayout quickViewHolder =
                	     (FrameLayout) findViewById(R.id.badge_holder_frame);

                QuickContactBadge quickView = new QuickContactBadge(this);
                quickView.assignContactUri(contactUri);
                quickView.setMode(ContactsContract.QuickContact.MODE_MEDIUM);
                quickView.setImageResource(R.drawable.compass_base);
                quickViewHolder.addView(quickView);
                break;
            }
        }
    }
}