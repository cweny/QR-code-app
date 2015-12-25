package com.makemyandroidapp.qrmaker;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class QRSelect extends Activity{
	
	private SharedPreferences mainValue;
	private TableLayout valuesScrollView;
	private DBTools dbTools;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_qr);
		
		mainValue = getSharedPreferences("main_file",MODE_PRIVATE);
		valuesScrollView = (TableLayout)findViewById(R.id.select_layout);
		dbTools = new DBTools(this);
		ArrayList<HashMap<String, String>> data = dbTools.getAllData();
		
		if(data.isEmpty()){
			LayoutInflater inflater = getLayoutInflater();
			
			TextView newValue = (TextView)inflater.inflate(R.layout.textview_qr, null);
			newValue.setText("Memory Empty");
			newValue.setGravity(Gravity.CENTER);
			newValue.setTextSize(20);
			valuesScrollView.addView(newValue);
		}else{
			for(int i=0; i<data.size(); i++){
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				View newValue = inflater.inflate(R.layout.select_info_qr, null);
				
				TextView newValueText = (TextView)newValue.findViewById(R.id.textView1);
				newValueText.setText(data.get(i).get("title"));
				
				Button selectButton = (Button)newValue.findViewById(R.id.button1);
				selectButton.setOnClickListener(new Button.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						SharedPreferences.Editor mainEditor = mainValue.edit();
						
						TableRow tablerow = (TableRow)v.getParent();
						TextView text = (TextView)tablerow.findViewById(R.id.textView1);
						HashMap<String,String> data = dbTools.getData(text.getText().toString());
						String value = data.get("data1")+data.get("data2")+data.get("data3");
						
						mainEditor.clear();
						mainEditor.commit();
						mainEditor.putString("main", value);
						mainEditor.putString("key", text.getText().toString());
						mainEditor.commit();
						Intent intent = new Intent(QRSelect.this, QRActivity.class);
						startActivity(intent);
					}
				});
				
				valuesScrollView.addView(newValue);
			}
		}
	}
}
