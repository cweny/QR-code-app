package com.makemyandroidapp.qrmaker;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QREditInfo extends Activity{
	
	DBTools dbTools;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_info_qr);
		dbTools = new DBTools(this);
		String text = getIntent().getStringExtra(QREdit.EXTRA_MESSAGE);
		
		TextView title = (TextView)findViewById(R.id.title_textview);
		title.setText(text);
		TextView data1 = (TextView)findViewById(R.id.card_num);
		TextView data2 = (TextView)findViewById(R.id.exp_date);
		TextView data3 = (TextView)findViewById(R.id.code);
		HashMap<String,String> data;
		data = dbTools.getData(text);
		
		data1.setText(data.get("data1"));
		data2.setText(data.get("data2"));
		data3.setText(data.get("data3"));
		Button addButton = (Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout layout = (LinearLayout)v.getParent();
				TextView title = (TextView)layout.findViewById(R.id.title_textview);
				EditText data1 = (EditText)layout.findViewById(R.id.card_num);
				EditText data2 = (EditText)layout.findViewById(R.id.exp_date);
				EditText data3 = (EditText)layout.findViewById(R.id.code);
				
				String titleString = title.getText().toString();
				String data1String = data1.getText().toString();
				String data2String = data2.getText().toString();
				String data3String = data3.getText().toString();
				
				dbTools.editData(titleString, data1String, data2String, data3String);
				
				Toast.makeText(getApplicationContext(), "Edited Succesfully", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}
