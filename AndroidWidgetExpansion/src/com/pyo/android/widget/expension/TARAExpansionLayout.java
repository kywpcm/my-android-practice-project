/*
 *  TARAIconValueObject의 정보를 표현할 한 행에 해당 하는 안드로이드 뷰 확장
 */
package com.pyo.android.widget.expension;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TARAExpansionLayout extends LinearLayout{
	private TextView  memberText;
	private ImageView memberIcon;

	public TARAExpansionLayout(Context context, TARAIconValueObject taraValueObject){
		super(context);
		this.setOrientation(HORIZONTAL);

		memberIcon = new ImageView(context);
		memberIcon.setImageDrawable(taraValueObject.getMemberIcon());
		memberIcon.setPadding(0, 2, 5, 0);
		addView(memberIcon, new LinearLayout.LayoutParams(
		                LayoutParams.WRAP_CONTENT,
				        LayoutParams.WRAP_CONTENT));

		memberText = new TextView(context);
		memberText.setTextSize(20);
		
		memberText.setTextColor(Color.argb(128, 0,0,0));
		memberText.setText(taraValueObject.getMemberName());
		
		addView(memberText, new LinearLayout.LayoutParams(
		                             LayoutParams.WRAP_CONTENT,
				                     LayoutParams.WRAP_CONTENT));
	}
	public void setMemberText(String memberName){
		memberText.setText(memberName);
	}
	public void setMemberIcon(Drawable memberImage){
		memberIcon.setImageDrawable(memberImage);
	}
}