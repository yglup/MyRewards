package com.augurs.myrewards.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import com.augurs.myrewards.R;
import com.augurs.myrewards.dataclasses.AuthenticateUserData;
import com.augurs.myrewards.dataclasses.NearByMerchantData;
import com.augurs.myrewards.dataclasses.ResetPasswordData;
import com.augurs.myrewards.manager.AuthenticationManager;
import com.augurs.myrewards.manager.PasswordManager;
import com.augurs.myrewards.utilities.GlobalVariables;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.GraphUser;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LoginActivity extends Activity 
{

	TextView textViewSignUp , textViewForgotPassword;
	Button buttonFacebook;
	Button buttonLogin;

	LocationManager locationManager;
	
	String promptEmailId = null;
	String status = null;
	String message = null;
	String emailId = "";
	String password = "";
	String fbId = "";
	String userId = null;
	
	String PREFS_FBID = "fbId";
	SharedPreferences preferences;

	EditText editTextEmail , editTextPassword;
	JSONObject jsonObject;

	String facebook_appId;// = null;

	List<AuthenticateUserData> authenticationList = new ArrayList<AuthenticateUserData>();
	List<ResetPasswordData> passList = new ArrayList<ResetPasswordData>();
	List<NearByMerchantData> nearList = new ArrayList<NearByMerchantData>();
	
	boolean isGPSEnabled = false;
	boolean isOnline = false;
	NetworkInfo networkInfo;
	ConnectivityManager connectivityManager;


	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private boolean validation = false;

	String latitude = "18.5203";
	String longitude = "73.8567";

	Resources res;
	private Facebook facebook = new Facebook("567243790051118");
	private AsyncFacebookRunner mAsyncRunner;
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);


		locationManager = (LocationManager) LoginActivity.this.getSystemService(Context.LOCATION_SERVICE);
		
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		Log.d("IS GPS ENABLED---------->" , " : " + isGPSEnabled);
		
		res = getResources();
		connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();//connection.isNetAvailable(SignUpActivity.this);

		pattern = Pattern.compile(EMAIL_PATTERN);
		
		isOnline = networkInfo.isConnected();

		if(isOnline)
		{
			mAsyncRunner = new AsyncFacebookRunner(facebook);
		}

		preferences = LoginActivity.this.getSharedPreferences(PREFS_FBID , 0 );
		facebook_appId = preferences.getString("facebook_id" , "");
		Log.d("PREFS FBID-------->" , " : " + facebook_appId);


		buttonLogin = (Button) findViewById(R.id.button_login);
		buttonFacebook = (Button) findViewById(R.id.btn_facebook);

		textViewSignUp = (TextView) findViewById(R.id.txtView_sign_up);
		textViewForgotPassword = (TextView) findViewById(R.id.textView_forgot_password);

		editTextEmail = (EditText) findViewById(R.id.editText_email);
		editTextPassword = (EditText) findViewById(R.id.editText_password);

		textViewSignUp.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View view) 
			{
				startActivity(new Intent(LoginActivity.this , SignUpActivity.class));
			}
		});


		textViewForgotPassword.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				final Dialog dialog = new Dialog(LoginActivity.this);

				dialog.setContentView(R.layout.prompt_reset_password);
				dialog.setTitle("Forgot Password!");
				final EditText editTextEmailId = (EditText) dialog.findViewById(R.id.editTextDialogUserInput);
				Button buttonPassword = (Button) dialog.findViewById(R.id.buttonSubmitPassword);
				buttonPassword.setOnClickListener(new OnClickListener() 
				{

					@Override
					public void onClick(View v) 
					{
						promptEmailId = editTextEmailId.getText().toString();

						new GetUserResetPassword().execute(promptEmailId);

						dialog.dismiss();

					}
				});
				dialog.show();

			}
		});

		buttonFacebook.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				emailId = editTextEmail.getText().toString();
				password = editTextPassword.getText().toString();

				// start Facebook Login
				loginTofacebook();			
				//new GetFacebookAuthentication().execute();

			}
		});

		buttonLogin.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				emailId = editTextEmail.getText().toString();
				password = editTextPassword.getText().toString();
				
				validation = validateEmail(emailId);
				
				if( isOnline)
				{
					Log.d("emailid---->" , " : " + emailId);
					Log.d("password---->" , " : " + password);
					if(emailId.equals("") || password.equals(""))
					{
						Toast.makeText(LoginActivity.this , "email id and password required" , Toast.LENGTH_LONG).show();
					}else
					{

						if(validation)
						{
							
							if( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)/*isGPSEnabled*/ )
							{
								GlobalVariables.emailId = emailId;
								new GetUserLoginAuthentication().execute(emailId , password);
							}else
							{
								AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

								alertDialog.setIcon(R.drawable.alert_icon);
								alertDialog.setTitle("GPS");
								alertDialog.setMessage("Enable your GPS !");
								alertDialog.setPositiveButton(" O K " , new DialogInterface.OnClickListener() 
								{

									@Override
									public void onClick(DialogInterface dialog, int which) 
									{
										Intent gpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
										startActivity(gpsIntent);
										
										dialog.cancel();
									}
								});

								alertDialog.show();
							}
														
						}else
						{
							AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

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
											
					}


				}else
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}



	public void loginToFacebook()
	{
		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() 
		{

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state, Exception exception) 
			{
				if (session.isOpened()) 
				{

					// make request to the /me API
					Request.newMeRequest(session, new Request.GraphUserCallback() 
					{

						// callback after Graph API response with user object
						@Override
						public void onCompleted(GraphUser user, Response response) 
						{
							if (user != null) 
							{
								Toast.makeText(LoginActivity.this , "fbid : " + user.getId() , Toast.LENGTH_LONG).show();

								//TextView welcome = (TextView) findViewById(R.id.welcome);
								//welcome.setText("Hello " + user.getId() + "!");
							}
						}


					}).executeAsync();
				}
			}
		});
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}*/

	public void loginTofacebook() 
	{
		Log.d("login to facebook--------> " , " : " + " facebook");
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) 
		{
			facebook.setAccessToken(access_token);
			Log.d("FB Sessions-----------> ( AYAN SINHA)", "" + facebook.isSessionValid());
			Toast.makeText(LoginActivity.this , "You are currently signed in " , Toast.LENGTH_LONG).show();
		}

		if (expires != 0) 
		{
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) 
		{
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() 
			{

				@Override
				public void onCancel() 
				{
					// Function to handle cancel event
					Log.d("Facebook onCancel()---------------->" , " onCancel()");
				}

				@Override
				public void onComplete(Bundle values) 
				{
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();

					Log.d("Facebook onComplete()---------------->" , " onComplete()");

					new GetFacebookAuthentication().execute();
				}

				@Override
				public void onError(DialogError error) 
				{
					// Function to handle error
					Log.d("Facebook onError()---------------->" , " onError()");

				}

				@Override
				public void onFacebookError(FacebookError fberror) 
				{
					// Function to handle Facebook errors
					Log.d("Facebook onFacebookError()---------------->" , " onFacebookError()");

				}

			});
		}


	}


	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() 
	{
		emailId = editTextEmail.getText().toString();
		password = editTextPassword.getText().toString();

		if(emailId.equals("") || password.equals(""))
		{



		}else
		{
			if(/*GlobalVariables.facebookAppId.equals("") || */GlobalVariables.facebookAppId == null)
			{

			}else
			{
				new GetFacebookAuthentication().execute();
			}
		}

	}

	/*************CLASS TO GET FACEBOOK AUTHENTICATION*************/

	public class GetFacebookAuthentication extends AsyncTask<String , Boolean , String>
	{
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setTitle("Authenticating User...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(final String... params) 
		{
			Log.d("doInBackground-->" , "...");

			mAsyncRunner.request("me", new RequestListener() 
			{
				@Override
				public void onComplete(String response, Object state)
				{
					Log.d("Profile", response);
					String json = response;
					try {
						// Facebook Profile JSON data
						JSONObject profile = new JSONObject(json);

						// getting name of the user
						final String name = profile.getString("name");
						GlobalVariables.facebookAppId = profile.getString("id");
						Log.d("GLOAL VARIABLES FACEBOOK APP ID USER-------->" , " : " + GlobalVariables.facebookAppId);
						// getting email of the user
						final String email = profile.getString("email");
						final String id = profile.getString("id");
						runOnUiThread(new Runnable()
						{

							@Override
							public void run() 
							{



								/*if(GlobalVariables.facebookAppId != null)
								{*/

								Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email + " fbid : " +id , Toast.LENGTH_LONG).show();
								/*	new GetUserLoginAuthentication().execute(GlobalVariables.facebookAppId);
								}else
								{
									new GetUserLoginAuthentication().execute(GlobalVariables.facebookAppId);
								}*/




							}

						});


					} catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onIOException(IOException e, Object state)
				{

				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e , Object state) 
				{
				}

				@Override
				public void onMalformedURLException(MalformedURLException e , Object state)
				{
				}

				@Override
				public void onFacebookError(FacebookError e, Object state) 
				{

				}
			});


			/*Session.openActiveSession(LoginActivity.this, true, new Session.StatusCallback() 
			{

				// callback when session changes state
				@Override
				public void call(Session session, SessionState state, Exception exception) 
				{
					if (session.isOpened()) 
					{

						//Request.newMeRequest(session, new Request.)

						// make request to the /me API
						Request.newMeRequest(session, new Request.GraphUserCallback() 
						{

							// callback after Graph API response with user object
							@Override
							public void onCompleted(GraphUser user, Response response) 
							{
								if (user != null) 
								{
									//TextView welcome = (TextView) findViewById(R.id.textView_forgot_password);
									//welcome.setText("Hello " + user.getName() + "!" + " , " + user.getId());
									Log.d("fbid----------->" , " : " + user.getId());

									preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);//context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
									Editor editor = preferences.edit();
									editor.putString("facebook_id", user.getId());
									editor.commit();
									try 
									{
										jsonObject = new JSONObject(response.getRawResponse());

										GlobalVariables.facebookAppId = jsonObject.getString("id");
										Log.d("jsonObject--->" , " : " + jsonObject.toString());
									} catch (JSONException e) 
									{
										e.printStackTrace();
									}
									Log.d("response------->" , " : " + response.getRawResponse());

									//GlobalVariables.facebookAppId = user.getId();
									//facebook_appId = user.getId();
									//Log.d("facebook appid--------->" , " : " + facebook_appId);

									//graphUser = user;
									//Toast.makeText(LoginActivity.this , "fb name : " + user.getName() , Toast.LENGTH_LONG).show();
									//startActivity(new Intent(LoginActivity.this , HomeActivity.class));
									Toast.makeText(LoginActivity.this , "fb name : " + user.getName() , Toast.LENGTH_LONG).show();
								}else
								{
									Toast.makeText(LoginActivity.this , "Error at authentication !" , Toast.LENGTH_LONG).show();
								}
							}



						}).executeAsync();
					}
				}
			});*/

			return null;
		}



		@Override
		protected void onPostExecute(String result)
		{
			//Log.d("JSON--->" , " : " +jsonObject.toString());
			//facebook_appId = jsonObject.getString("")



			progressDialog.dismiss();
			new GetUserLoginAuthentication().execute(GlobalVariables.facebookAppId);

			Log.d("GLOAL VARIABLES FACEBOOK APP ID USER-------->" , " : " + GlobalVariables.facebookAppId);
			/*if(GlobalVariables.facebookAppId != null)
			{	

				startActivity(new Intent(LoginActivity.this , HomeActivity.class));
			}else
			{
				Toast.makeText(LoginActivity.this , "Error at authentication !" , Toast.LENGTH_LONG).show();
			}*/
			super.onPostExecute(result);
		}
	}



	/*******CLASS TO GET USER LOGIN AUTHENTICATION***********/

	public class GetUserLoginAuthentication extends AsyncTask<String , Boolean , Integer>
	{


		ProgressDialog progressDialog;
		int index = 0;


		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setTitle("Authenticating User...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();

			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) 
		{
			authenticationList = AuthenticationManager.getUserRegistration(fbId, emailId, password);

			return null;
		}

		@Override
		protected void onPostExecute(Integer result) 
		{
			progressDialog.dismiss();


			message = authenticationList.get(index)._message;
			status = authenticationList.get(index)._status;
			Log.d("STATUS------->" , " : " + status);
			Log.d("MESSAGE------->" , " : " + message);

			if(status.equals("SUCCESS"))
			{

				Toast.makeText(LoginActivity.this , " " + authenticationList.get(index)._message , Toast.LENGTH_LONG).show();				

				GlobalVariables.emailId = emailId;
				userId = authenticationList.get(index)._userId;
				GlobalVariables.userId = userId;
				
				Log.d("USERID IN LOGIN ACTIVITY------->" , " : " + userId);
				
				savePreferences("emailId" , emailId);
				
				//startActivity(new Intent(LoginActivity.this , MainActivity.class));
				startActivity(new Intent(LoginActivity.this , ActivityParentMerchant.class));
				
				
				//new GetNearByMerchant().execute(latitude , longitude);

			}else if(status.equals("FAILED"))
			{
				Toast.makeText(LoginActivity.this , " " + authenticationList.get(index)._message , Toast.LENGTH_LONG).show();
			}

			super.onPostExecute(result);
		}

	}


	


	/*********CLASS TO GET RESET USER'S FORGOT PASSWORD*********/

	public class GetUserResetPassword extends AsyncTask<String , Boolean , Integer>
	{


		ProgressDialog progressDialog;
		int index = 0;


		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setTitle("Resetting Password...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();

			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) 
		{
			Log.d("PROMPT EMAIL ID-------->" , " : " + promptEmailId);
			passList = PasswordManager.getUserPasswordDetails(promptEmailId);

			return null;
		}

		@Override
		protected void onPostExecute(Integer result) 
		{
			progressDialog.dismiss();

			message = passList.get(index)._message;
			status = passList.get(index)._status;
			Log.d("STATUS------->" , " : " + status);
			Log.d("MESSAGE------->" , " : " + message);

			if(status.equals("SUCCESS"))
			{

				Toast.makeText(LoginActivity.this , " " + passList.get(index)._message , Toast.LENGTH_LONG).show();				

			}else if(status.equals("FAILED"))
			{
				Toast.makeText(LoginActivity.this , " " + passList.get(index)._message , Toast.LENGTH_LONG).show();
			}


			super.onPostExecute(result);
		}

	}

	public void savePreferences( String key , String value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

}
