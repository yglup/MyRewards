package com.augurs.myrewards.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.augurs.myrewards.dataclasses.RegisterUserData;

public class MyRewardsUserId 
{
	Context context;
	private SQLiteDatabase database;
	private MyRewardsSqliteHelper databaseHelper;



	public MyRewardsUserId(Context context) 
	{
		this.context = context;
	}

	public MyRewardsUserId open() throws SQLException
	{
		databaseHelper = new MyRewardsSqliteHelper(context);
		return this;	
	}



	/********table name*********/
	private static final String TABLE_USER_ID = "MyRewardsUserId";


	/******column name*******/

	private static final String KEY_ROW_ID = "id";
	private static final String KEY_USER_ID = "userId";
	private static final String KEY_EMAIL_ID = "emailId";
	private static final String KEY_USER_NAME = "userName";


	/*******create table**********/

	public static final String CREATE_TABLE_USER_ID = "CREATE TABLE " + TABLE_USER_ID + "(" + KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_USER_ID + " TEXT," + KEY_EMAIL_ID + " TEXT," + KEY_USER_NAME + " TEXT " + ")";


	public void insertUserId(List<RegisterUserData> userList , String emailId , String userName)
	{
		try
		{
			open();

			SQLiteDatabase db = databaseHelper.getWritableDatabase();

			for(int i = 0 ; i < userList.size() ; i++)
			{
				ContentValues values = new ContentValues();

				values.put(KEY_USER_ID , userList.get(i)._userId);
				values.put(KEY_EMAIL_ID , emailId);
				values.put(KEY_USER_NAME , userName);
				
				db.insert(TABLE_USER_ID , null , values);

			}

			databaseHelper.close();
			db.close();	

		}catch(Exception e)
		{			
			Log.e("ERROR IN INSERTING INTO TABLE " + TABLE_USER_ID , " : " + e.getMessage());
		}
	}

	/***********CLOSE DATABASE***********/

	public void closeDB()
	{
		open();
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		if (db != null && db.isOpen())
			db.close();
		databaseHelper.close();
	}



}
