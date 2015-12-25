package com.makemyandroidapp.qrmaker;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class QRDelete extends Activity{
	private TableLayout valuesScrollView;
	private DBTools dbTools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_qr);
		dbTools = new DBTools(this);
		valuesScrollView = (TableLayout)findViewById(R.id.delete_layout);
		ArrayList<HashMap<String, String>> data = dbTools.getAllData();
		if(data.isEmpty()){
			LayoutInflater inflater = getLayoutInflater();
			
			View newValue = inflater.inflate(R.layout.textview_qr, null);
			((TextView)newValue).setText("Memory Empty");
			((TextView)newValue).setGravity(Gravity.CENTER);
			((TextView)newValue).setTextSize(20);
			valuesScrollView.addView(newValue);
		}else{
			for(int i=0; i<data.size(); i++){
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				View newValue = inflater.inflate(R.layout.delete_info_qr, null);
				
				TextView newValueText = (TextView)newValue.findViewById(R.id.textView1);
				newValueText.setText(data.get(i).get("title"));
				
				Button deleteButton = (Button)newValue.findViewById(R.id.button1);
				deleteButton.setOnClickListener(new Button.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						TableRow tablerow = (TableRow)v.getParent();
						TextView text = (TextView)tablerow.findViewById(R.id.textView1);
						
						dbTools.deleteData(text.getText().toString());
						SharedPreferences mainValue = getSharedPreferences("main_file",MODE_PRIVATE);
						String key = mainValue.getString("key", null);
						
						if(key!=null && key.equals(text.getText().toString())){
							SharedPreferences.Editor mainEditor = mainValue.edit();
							mainEditor.clear();
							mainEditor.commit();
						}
						
						valuesScrollView.removeView(tablerow);
					}
				});
				
				valuesScrollView.addView(newValue);
			}
		}
	}
			
	
}
