package com.makemyandroidapp.qrmaker;

import java.util.HashMap;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class QRAdd extends Activity {
	private DBTools dbTools;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_qr);
	
		dbTools = new DBTools(this);
		Button addButton = (Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				LinearLayout layout = (LinearLayout)v.getParent();
				EditText title = (EditText)layout.findViewById(R.id.title);
				EditText data1 = (EditText)layout.findViewById(R.id.card_num);
				EditText data2 = (EditText)layout.findViewById(R.id.exp_date);
				EditText data3 = (EditText)layout.findViewById(R.id.code);
				
				String titleString = title.getText().toString();
				String data1String = data1.getText().toString();
				String data2String = data2.getText().toString();
				String data3String = data3.getText().toString();
				
				HashMap<String, String> data = new HashMap<String, String>();
				data = dbTools.getData(titleString);
				if(data.isEmpty()){
					dbTools.insertData(titleString, data1String, data2String, data3String);
					Toast.makeText(getApplicationContext(), "Save Successfull", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "Already exists", Toast.LENGTH_SHORT).show();
				}
				SharedPreferences mainValue = getSharedPreferences("main_file",MODE_PRIVATE);
				String main = mainValue.getString("main", null);
				
				if(main == null){
					SharedPreferences.Editor mainEditor = mainValue.edit();
					mainEditor.putString("main", data1String+data2String+data3String);
					mainEditor.putString("key", titleString);
					mainEditor.commit();
				}
			}
		});
	}

}
