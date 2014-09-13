package com.augurs.myrewards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyRewardsSqliteHelper extends SQLiteOpenHelper 
{

	public SQLiteDatabase database;
	// Database Version
	private static final int DATABASE_VERSION = 1;

	Context mcontext;

	// Database Name
	private static final String DATABASE_NAME = "MyRewardsDB.db";



	public MyRewardsSqliteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mcontext = context;
	}


	public SQLiteDatabase openDB()
	{
		database = this.getWritableDatabase();
		return database;
	}



	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(MyRewardsUserId.CREATE_TABLE_USER_ID);
		
		Log.d("OnCreate SQLITE------>" , "onCreate database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + MyRewardsUserId.CREATE_TABLE_USER_ID);
		Log.d("OnUpGrade SQLITE------>" , "onUpgrade database");
		
		onCreate(db);
	}

}
