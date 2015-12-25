package com.makemyandroidapp.qrmaker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
//import android.text.ClipboardManager;
//import android.content.ClipboardManager;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class QRActivity extends Activity {
	ImageLoader imgLoader;
	ImageView qrImg;
	String copiedStr;
	TextView qrTxt;
	private SharedPreferences savedValues;
	
	String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl=";
	String fullUrl = BASE_QR_URL;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        
        savedValues = getSharedPreferences("main_file",MODE_PRIVATE);
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);
        
        qrImg = (ImageView)findViewById(R.id.qrImg);
        qrTxt = (TextView)findViewById(R.id.qrTxt);
        
		CharSequence clipTxt = savedValues.getString("main", null);

        if((null != clipTxt) && (clipTxt.length() > 0)){
        	try {
        		qrTxt.setText(clipTxt);
        		copiedStr = clipTxt.toString();
				fullUrl += URLEncoder.encode(copiedStr, "UTF-8");
				imgLoader.displayImage(fullUrl, qrImg);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        	
        }else{ 
        	
        	View progressBar = findViewById(R.id.qrPrg);
        	((RelativeLayout)progressBar.getParent()).removeView(progressBar);
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	
        	builder.setTitle("QRMaker")
        	.setCancelable(true)
        	.setMessage("There is nothing selected")
        	.setIcon(R.drawable.nuke)
        	.setNeutralButton("Okay", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}

        	});
        	
        	AlertDialog diag = builder.create();
        	diag.show();
        }
    } 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_qr, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
        	Intent intent_settings = new Intent(this, QRSettings.class);
			this.startActivity(intent_settings);
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void selectInfo(View view){
    	
    	Intent intent = new Intent(this, QRSelect.class);
    	startActivity(intent);
    }
}
