/*
 *  TARA ����� ���� Icon Image�� �̸����� ���� ����ü
 */
package com.pyo.android.widget.expension;

import android.graphics.drawable.Drawable;

public class TARAIconValueObject{
	private String memberName;
	private Drawable memberIcon;
	private boolean selectable = true;
    
	public TARAIconValueObject(String memberName, Drawable memberIcon){
		this.memberName = memberName;
		this.memberIcon = memberIcon;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Drawable getMemberIcon() {
		return memberIcon;
	}
	public void setMemberIcon(Drawable memberIcon) {
		this.memberIcon = memberIcon;
	}
	public boolean isSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
}