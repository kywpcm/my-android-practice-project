package com.example.mycalculator;

import com.example.mycalculator.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button one;
	Button two;
	Button three;
	Button four;
	Button five;
	Button six;
	Button seven;
	Button eight;
	Button nine;
	Button zero;
	Button plus;
	Button minus;
	Button divide;
	Button multiply;
	Button equal;
	Button backspace;
	EditText calcEditText;
	String string;
	String temp;
	int length;
	int yunsan;
	float sum=0;
	Boolean on=false;
	Boolean clean=false;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calc_activity);

		one = (Button) findViewById(R.id.btn1);
		two = (Button) findViewById(R.id.btn2);
		three = (Button) findViewById(R.id.btn3);
		four = (Button) findViewById(R.id.btn4);
		five = (Button) findViewById(R.id.btn5);
		six = (Button) findViewById(R.id.btn6);
		seven = (Button) findViewById(R.id.btn7);
		eight = (Button) findViewById(R.id.btn8);
		nine = (Button) findViewById(R.id.btn9);
		zero = (Button) findViewById(R.id.btn0);
		plus = (Button) findViewById(R.id.btnPlus);
		minus = (Button) findViewById(R.id.btnMinus);
		divide = (Button) findViewById(R.id.btnDivider);
		multiply = (Button) findViewById(R.id.btnMultiplier);
		equal = (Button) findViewById(R.id.btnEqual);
		backspace = (Button) findViewById(R.id.btnErase);
		calcEditText = (EditText) findViewById(R.id.calcWindow);

		one.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("1");
			}
		});

		two.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("2");
			}
		});

		three.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("3");
			}
		});

		four.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("4");
			}
		});

		five.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0)
			{
				function("5");
			}
		});

		six.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("6");
			}
		});

		seven.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0)
			{
				function("7");
			}
		});

		eight.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0)
			{
				function("8");
			}
		});

		nine.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function("9");
			}
		});

		zero.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				if(on!=true) function("0");
			}
		});

		plus.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function2("+");
			}
		});

		minus.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function2("-");
			}
		});

		multiply.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) 
			{
				function2("×");	
			}
		});

		divide.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				function2("÷");
			}
		});

		equal.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0) 
			{
				string = calcEditText.getText().toString();
				length=string.length();
				String last=string.substring(length-1,length);
				int j=0;
				int k=0;
				int point;
				int pointTemp;
				Boolean first = true;



				for(int i=0;i<length;i++)
				{
					point=string.codePointAt(i);
					if(point==215||point==247||point==43||point==45)
					{
						if(first==true)
						{
							sum=sum+Integer.parseInt(string.substring(0,i));
							first=false;
						}

						if(first==false)
						{
							if(point==43)
							{
								for(j=i+1;j<length;j++)
								{
									pointTemp=string.codePointAt(j);
									if(pointTemp==215||pointTemp==247||pointTemp==43||pointTemp==45) break;
								}

								sum=sum+Integer.parseInt(string.substring(i+1,j));
							}

							else if(point==45)
							{
								for(j=i+1;j<length;j++)
								{
									pointTemp=string.codePointAt(j);
									if(pointTemp==215||pointTemp==247||pointTemp==43||pointTemp==45) break;
								}

								sum=sum-Integer.parseInt(string.substring(i+1,j));
							}

							else if(point==215)
							{
								for(j=i+1;j<length;j++)
								{
									pointTemp=string.codePointAt(j);
									if(pointTemp==215||pointTemp==247||pointTemp==43||pointTemp==45) break;
								}

								sum=sum*Integer.parseInt(string.substring(i+1,j));
							}

							else if(point==247)
							{
								for(j=i+1;j<length;j++)
								{
									pointTemp=string.codePointAt(j);
									if(pointTemp==215||pointTemp==247||pointTemp==43||pointTemp==45) break;
								}

								sum=sum/Integer.parseInt(string.substring(i+1,j));
							}
						}

					}


				}
//				곱하기=215 나누기=247 더하기 =43 빼기=45


				calcEditText.setText(""+sum);
				sum=0;
				clean=true;
			}
		});

		backspace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(clean==true){
					calcEditText.setText("");
					clean=false;
				}
				else if(!calcEditText.getText().toString().equals("")){ 
					calcEditText.setText(calcEditText.getText().toString().substring(0, calcEditText.getText().toString().length()-1));
					if(on==true) on=false;
				}
			}
		});
	}


	void function(String a){
		string = calcEditText.getText().toString();
		length=calcEditText.getText().toString().length();

		if(string.equals("")||string.equals("0")){
			calcEditText.setText(""+a);
			on=false;
		}
		else if(clean==true){
			calcEditText.setText(""+a);
			clean=false;
		}
		else{
			calcEditText.setText(string+a);
			on=false;
		}
	}

	void function2(String a){
		string = calcEditText.getText().toString();
		if(clean==true)
		{
			calcEditText.setText("");
			clean=false;

		}
		else if(on==false){
			calcEditText.setText(string+a);
			on=true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

}
