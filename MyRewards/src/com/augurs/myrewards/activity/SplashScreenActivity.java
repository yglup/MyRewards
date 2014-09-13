package com.augurs.myrewards.activity;


import com.augurs.myrewards.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

public class SplashScreenActivity extends Activity 
{
	private static int SPLASH_TIME = 3000;
	
	String emailPrefs = null;
	
	SharedPreferences sharedPreferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		SplashScreenActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splashscreen);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
		emailPrefs = sharedPreferences.getString("emailId" , null);
		
		Log.d("SHARED PREFRENCES------->" , " : " + emailPrefs);
		
		new Handler().postDelayed(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				
				startActivity ( new Intent(SplashScreenActivity.this , LoginActivity.class));
				finish();
				
			}
		}, SPLASH_TIME);
	}
}
