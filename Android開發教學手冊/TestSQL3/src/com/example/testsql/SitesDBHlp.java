package com.example.testsql;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SitesDBHlp extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "sites";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "sitesInfo";
	private static final String TABLE_CREATE = 
					"CREATE TABLE " + TABLE_NAME + " ( " +
					" id TEXT NOT NULL, " +
					" name TEXT NOT NULL, " +
					" phoneNo TEXT, " +
					" address TEXT, PRIMARY KEY (id)); ";
	private static final String COL_id = "id";
	private static final String COL_name = "name";
	private static final String COL_phoneNo = "phoneNo";
	private static final String COL_address = "address";
	
	public SitesDBHlp(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	
	public void fillDB() {
		SQLiteDatabase db = getWritableDatabase();		
		ContentValues[] values = new ContentValues[3];
		for(int i=0; i<values.length; i++)
			values[i] = new ContentValues();
		
		values[0].put("id", "yangmingshan");
		values[0].put("name", "�����s��a����޲z�B");
		values[0].put("phoneNo", "02-28613601");
		values[0].put("address", "�x�_���_��Ϧˤl���1��20��");
		
		values[1].put("id", "yushan");
		values[1].put("name", "�ɤs��a����޲z�B");
		values[1].put("phoneNo", "049-2773121");
		values[1].put("address", "�n�뿤�����m���s���@�q300��");
		
		values[2].put("id", "taroko");
		values[2].put("name", "�Ӿ|�հ�a����޲z�B");
		values[2].put("phoneNo", "03-8621100");
		values[2].put("address", "�Ὤ���q�L�m258��");	
		
		for(ContentValues row : values){
			db.insert(TABLE_NAME, null, row);
		}	
		db.close();
	}
	
	 
	public ArrayList<Site> getAllSites(){
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = {COL_id, COL_name, COL_phoneNo, COL_address};
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<Site> sites = new ArrayList<Site>();
		while(cursor.moveToNext()){
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			String phoneNo = cursor.getString(2);
			String address = cursor.getString(3);			
			Site site = new Site(id, name, phoneNo, address);
			sites.add(site);			
		}
		cursor.close();
		db.close();
		return sites;		
	}
}
