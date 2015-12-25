package com.makemyandroidapp.qrmaker;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class QREdit extends Activity{
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private TableLayout tableLayout;
	private DBTools dbTools;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_qr);
		
		dbTools = new DBTools(this);
		tableLayout = (TableLayout)findViewById(R.id.select_layout);
		ArrayList<HashMap<String, String>> data = dbTools.getAllData();
		if(data.isEmpty()){
			LayoutInflater inflater = getLayoutInflater();
			TextView text = (TextView)inflater.inflate(R.layout.textview_qr, null);
			text.setText("Memory Empty");
			text.setGravity(Gravity.CENTER);
			text.setTextSize(20);
			
			tableLayout.addView(text);
			
		}else{
			for(int i=0; i<data.size(); i++){
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				View newValue = inflater.inflate(R.layout.edit_qr, null);
				
				TextView newValueText = (TextView)newValue.findViewById(R.id.textView1);
				newValueText.setText(data.get(i).get("title"));
				
				Button selectButton = (Button)newValue.findViewById(R.id.button1);
				selectButton.setOnClickListener(new Button.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(QREdit.this, QREditInfo.class);
						TextView text = (TextView)((TableRow)v.getParent()).findViewById(R.id.textView1);
						String message = text.getText().toString();
						intent.putExtra(EXTRA_MESSAGE, message);
					    startActivity(intent);
					}
				});
				
				tableLayout.addView(newValue);
			}
		}
	}
}
