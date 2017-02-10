package com.pyo.android.widget.expension;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TARACustomBaseAdapter extends BaseAdapter{
  private Context currentContext;
  private ArrayList<TARAIconValueObject> adapterList = new ArrayList<TARAIconValueObject>();
  
  public TARACustomBaseAdapter(Context context){
     currentContext = context;
  }
  public void addItem(TARAIconValueObject taraObject){
	  adapterList.add(taraObject);
  }
  public void setListItems(ArrayList<TARAIconValueObject> adapterList){
		this.adapterList = adapterList;
  }
  //BaseAdapter Ȯ��� �ʼ� ���� �޼ҵ�
  public int getCount(){
	  return adapterList.size();
  }
  //�ʼ� ���� �޼ҵ�
  public Object getItem(int index){
	  return  adapterList.get(index);
  }
  //�ʼ� ���� �޼ҵ�
  //���� ���ڰ����� ������ �ε����� ���� ��ġ
  public long getItemId(int position) {
		return position;
  }
  public boolean areAllItemsSelectable(){
		return false;
  }
  public boolean isSelectable(int position) {
	 try{
		return adapterList.get(position).isSelectable();
	 }catch(IndexOutOfBoundsException ex) {
			return false;
	 }
  }
  /*
   *  ����Ʈ�� �� ���� ���� �������ϴ� �ݹ� �޼ҵ�
   */
  public View getView(int position, View convertView, ViewGroup parent){
	  TARAExpansionLayout taraLayout;
		if (convertView == null){
			taraLayout = new TARAExpansionLayout(currentContext, adapterList.get(position));
		} else {
			taraLayout = (TARAExpansionLayout)convertView;
			taraLayout.setMemberText(adapterList.get(position).getMemberName());
			taraLayout.setMemberIcon(adapterList.get(position).getMemberIcon());
		}
		return taraLayout;
	}
}