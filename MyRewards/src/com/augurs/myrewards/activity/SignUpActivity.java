package com.augurs.myrewards.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.augurs.myrewards.R;
import com.augurs.myrewards.database.MyRewardsSqliteHelper;
import com.augurs.myrewards.database.MyRewardsUserId;
import com.augurs.myrewards.dataclasses.RegisterUserData;
import com.augurs.myrewards.manager.UserRegisterManager;
import com.augurs.myrewards.utilities.ConnectionUtilities;
import com.augurs.myrewards.utilities.GlobalVariables;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity 
{

	String merchantId = "1";
	String firstName = "";
	String fullName = "";
	String lastName = "";
	String emailId = "";
	String password = "";
	String fbId = "";
	String udId = "";
	String source = "";
	String age = "";
	String gender = "";
	String address = "";
	String country = "";
	String state = "";
	String city = "";
	String zipCode = "";
	String aboutMe = "";
	String phone = "";


	String userId = null;


	String status = null;
	String message = null;


	SQLiteDatabase database;
	MyRewardsSqliteHelper datahelper;
	MyRewardsUserId rewardsDatabase;




	boolean isOnline;

	EditText editTextFL_Name , editTextEmail , editTextPassword , editTextPhone;
	Button buttonSignUp;
	NetworkInfo networkInfo;
	ConnectivityManager connectivityManager;
	ConnectionUtilities connection;

	List<RegisterUserData> registerUserList = new ArrayList<RegisterUserData>();

	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private boolean validation = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		rewardsDatabase = new MyRewardsUserId(SignUpActivity.this);
		datahelper = new MyRewardsSqliteHelper(SignUpActivity.this);
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		buttonSignUp = (Button) findViewById(R.id.button_signup);

		editTextFL_Name = (EditText) findViewById(R.id.editText_firstname);
		editTextEmail= (EditText) findViewById(R.id.editText_email_signup);
		editTextPassword = (EditText) findViewById(R.id.editText_password_signup);
		editTextPhone = (EditText) findViewById(R.id.editText_phone);

		connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();

		isOnline = networkInfo.isConnected();
		buttonSignUp.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View view) 
			{
				fullName = editTextFL_Name.getText().toString();
				emailId = editTextEmail.getText().toString();
				password = editTextPassword.getText().toString();
				phone = editTextPhone.getText().toString();
				
				validation = validateEmail(emailId);
				
				
				if( isOnline)
				{
					Log.d("EMAIL VALIDATION--------->" , " : " + validation);
					if( validation)
					{
						new GetRegisterUserData().execute(emailId , password , firstName , lastName);
					}else
					{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

						alertDialog.setIcon(R.drawable.alert_icon);
						alertDialog.setTitle("Validation");
						alertDialog.setMessage("Email id is not correct!");
						alertDialog.setPositiveButton(" O K " , new DialogInterface.OnClickListener() 
						{

							@Override
							public void onClick(DialogInterface dialog, int which) 
							{
								dialog.dismiss();
							}
						});

						alertDialog.show();

					}
				}else
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

					alertDialog.setIcon(R.drawable.alert_icon);
					alertDialog.setTitle("Online Connection");
					alertDialog.setMessage("Poor Internet Connectivity");
					alertDialog.setPositiveButton(" O K " , new DialogInterface.OnClickListener() 
					{

						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							dialog.dismiss();
						}
					});

					alertDialog.show();

				}

			}
		});



	}

	/***********EMAIL VALIDATION***********/
	
	public boolean validateEmail(String email)
	{
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	
	
	public class GetRegisterUserData extends AsyncTask<String , Boolean , Integer>
	{
		ProgressDialog progressDialog;
		int index = 0;


		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(SignUpActivity.this);
			progressDialog.setTitle("Registering User...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();		

			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) 
		{
			fullName = editTextFL_Name.getText().toString();
			emailId = editTextEmail.getText().toString();
			password = editTextPassword.getText().toString();
			phone = editTextPhone.getText().toString();

			registerUserList = UserRegisterManager.getUserRegistration(merchantId, fullName, emailId, lastName, password, fbId, udId, source, age, gender, address, country, state, city, zipCode, aboutMe);

			return null;
		}


		@Override
		protected void onPostExecute(Integer result) 
		{
			progressDialog.dismiss();

			message = registerUserList.get(index)._message;
			status = registerUserList.get(index)._status;
			userId = registerUserList.get(index)._userId;

			rewardsDatabase.open();
			
			fullName = editTextFL_Name.getText().toString();
			emailId = editTextEmail.getText().toString();
			
			GlobalVariables.emailId = emailId;
			
			rewardsDatabase.insertUserId(registerUserList , emailId , fullName);
			
			rewardsDatabase.closeDB();
			datahelper.close();
			
			
			Log.d("USER ID------->" , " : " + userId);
			Log.d("STATUS-------->" , " : " + status);
			Log.d("MESSAGE------->" , " : " + message);



			if(status.equals("SUCCESS"))
			{

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

				alertDialog.setIcon(R.drawable.alert_icon);
				alertDialog.setTitle("My Rewards");
				alertDialog.setMessage(registerUserList.get(index)._message);
				alertDialog.setPositiveButton(" O K " , new DialogInterface.OnClickListener() 
				{

					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.dismiss();
						startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
					}
				});

				alertDialog.show();

			}else if(status.equals("FAILED"))
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

				alertDialog.setIcon(R.drawable.alert_icon);
				alertDialog.setTitle("My Rewards");
				alertDialog.setMessage(registerUserList.get(index)._message);
				alertDialog.setPositiveButton(" O K " , new DialogInterface.OnClickListener() 
				{

					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.dismiss();
					}
				});

				alertDialog.show();
			}


			super.onPostExecute(result);
		}

	}

}
