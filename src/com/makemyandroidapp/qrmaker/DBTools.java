package com.makemyandroidapp.qrmaker;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools {
	DBHelper dbHelper;
	
	public DBTools(Context context){
		dbHelper = new DBHelper(context);
	}
	public long insertData(String title, String data1, String data2, String data3){
		SQLiteDatabase db= dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("title", title);
		contentValues.put("data1", data1);
		contentValues.put("data2", data2);
		contentValues.put("data3", data3);
		return db.insert("data", null, contentValues);
		
	}
	
	public void deleteData(String title){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String deleteQuery = "DELETE FROM data WHERE title='"+title+"'";
		db.execSQL(deleteQuery);
	}
	
	public int editData(String title, String data1, String data2, String data3){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("title", title);
		contentValues.put("data1", data1);
		contentValues.put("data2", data2);
		contentValues.put("data3", data3);
		
		return db.update("data", contentValues, "title"+" = ?", new String[]{title});
	}
	
	public ArrayList<HashMap<String,String>> getAllData(){
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		String selectQuery = "SELECT * FROM data";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do {
				HashMap<String,String> data = new HashMap<String, String>();
				data.put("title", cursor.getString(1));
				data.put("data1", cursor.getString(2));
				data.put("data2", cursor.getString(3));
				data.put("data3", cursor.getString(4));
				
				dataList.add(data);
			} while (cursor.moveToNext());
		}
		return dataList;
	}
	
	public HashMap<String, String> getData(String title){
		HashMap<String,String> data = new HashMap<String, String>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String selectQuery = "SELECT * FROM data where title='"+title+"'";
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			do {
				data.put("title", cursor.getString(1));
				data.put("data1", cursor.getString(2));
				data.put("data2", cursor.getString(3));
				data.put("data3", cursor.getString(4));

			} while (cursor.moveToNext());
		}
		
		return data;
	}
	static class DBHelper extends SQLiteOpenHelper{
		
		private final static String DB_NAME = "database";
		private final static int VERSION = 1;

		
		public DBHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String query = "CREATE TABLE data ( _id INTEGER PRIMARY KEY, title TEXT, data1 TEXT, data2 TEXT, data3 TEXT)" ;
			db.execSQL(query);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			String query ="DROP TABLE IF EXISTS data";
			db.execSQL(query);
			onCreate(db);
		}
		
		
	}

}
