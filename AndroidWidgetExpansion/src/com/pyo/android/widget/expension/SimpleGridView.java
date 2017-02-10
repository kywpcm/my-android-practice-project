package com.pyo.android.widget.expension;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SimpleGridView extends Activity {
	private static  String[] numbers = 
	   {"0","1", "2", "3", "4", "5", "6", "7", "8", "9",
		"a", "b", "c","d","e","f","10","11","12","13",
		"14","15","16","17","18","19","1a","1b","1c",
		"1d","1e","1f","20","21","22","23","24",
		"25","26","27","28","29","2a"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.grid_view_layout);
        GridView grid = (GridView)findViewById(R.id.grid_view);
    
        //R.layout.bigtextview는 각 테이블에 사용할 텍스트형식의 레이아웃
        grid.setAdapter(new ArrayAdapter<String>(
        		this, R.layout.bigtextview, numbers));
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 public void onItemClick(AdapterView<?> parent, View view,
			                              int position, long id) {
			TextView text = (TextView) view;
			String num = (String) text.getText();
			num = Integer.toHexString((Integer.parseInt(num,16) + 1));
			text.setText(num);
		}
	  });
	}
}
