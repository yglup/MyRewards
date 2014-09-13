package com.augurs.myrewards.activity;

import java.util.ArrayList;
import java.util.List;
import com.augurs.myrewards.R;
import com.augurs.myrewards.adapter.MerchantAdapter;
import com.augurs.myrewards.dataclasses.NearByMerchantData;
import com.augurs.myrewards.dataclasses.UserProfileInfo;
import com.augurs.myrewards.dataclasses.UserUpdateProfileInfo;
import com.augurs.myrewards.manager.MerchantManager;
import com.augurs.myrewards.utilities.GlobalVariables;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityParentMerchant extends Activity implements LocationListener
{
	
	String updateFirstName = null;
	String updateEmailId = null;
	String updateGender = null;
	String updateAddress = null;
	String updatePhone = null;
	String userId = null;

	String status = null;
	String message = null;

	String udid , source , age , gender , country , state , city , zipCode , aboutMe , lastName = "";
	
	List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();
	List<UserUpdateProfileInfo> userUpdateList = new ArrayList<UserUpdateProfileInfo>();	
	
	ListView listViewNearByMerchant , listViewNearbyShop , listViewMyRewards;
	
	Button buttonNearByMerchant ,  buttonNearByShop , buttonMyRewards , buttonUserProfile , buttonUpdateProfile;
	
	RelativeLayout relativeLayoutNearByMerchant , relativeLayoutNearByShop , relativeLayoutMyRewards , relativeLayoutUserProfile;
	
	List<NearByMerchantData> nearList = new ArrayList<NearByMerchantData>();
	MerchantAdapter merchantAdapter;
	
	LocationManager locationManager;
	String provider = null;
	Location location;
	
	String fullName = null;
	String USERID = null;
	String latitude = null;
	String longitude= null;
	
	boolean isOnline = false;
	NetworkInfo networkInfo;
	ConnectivityManager connectivityManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.updated_nearbymerchant);
		
		listViewNearByMerchant = (ListView) findViewById(R.id.listViewNearByMerchant);
		listViewNearbyShop = (ListView) findViewById(R.id.listViewNearByShop);
		listViewMyRewards = (ListView) findViewById(R.id.listViewMyRewards);
		
		relativeLayoutNearByMerchant = (RelativeLayout) findViewById(R.id.relativeNearByMerchant);
		relativeLayoutNearByShop = (RelativeLayout) findViewById(R.id.relativeNearByShop);
		relativeLayoutMyRewards = (RelativeLayout) findViewById(R.id.relativeMyRewards);
		relativeLayoutUserProfile = (RelativeLayout) findViewById(R.id.relativeUpdateUserProfile);
		
		buttonNearByMerchant = (Button) findViewById(R.id.buttonUpdatedNearByMerchant);
		buttonNearByShop = (Button) findViewById(R.id.buttonUpdatedShop);
		buttonMyRewards = (Button) findViewById(R.id.buttonUpdatedMyRewards);
		buttonUserProfile = (Button) findViewById(R.id.buttonUpdatedUserProfile);
		
		buttonNearByMerchant.setBackgroundResource(R.drawable.appointment_button_hover);
		
		buttonNearByMerchant.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v)
			{
				updateButtonsDisplayOnClick();
				buttonNearByMerchant.setBackgroundResource(R.drawable.appointment_button_hover);
			}
		});
		
		buttonNearByShop.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v)
			{
				updateButtonsDisplayOnClick();
				buttonNearByShop.setBackgroundResource(R.drawable.queue_button_hover);
			}
		});
		
		buttonMyRewards.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v)
			{
				updateButtonsDisplayOnClick();
				buttonMyRewards.setBackgroundResource(R.drawable.home_button_hover);
				
			}
		});
		
		buttonUserProfile.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v)
			{
				updateButtonsDisplayOnClick();
				buttonUserProfile.setBackgroundResource(R.drawable.profile_button_hover);
			}
		});
		
		
		/*************list view item on click for near by merchant***************/
		
		listViewNearByMerchant.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent , View view , int position , long id) 
			{
				Log.d("position-------->" , " : " + position);
				
				fullName = nearList.get(position)._business_name + " " + nearList.get(position)._name;
				
				Intent intent = new Intent(ActivityParentMerchant.this , MerchantDetailsActivity.class);
				intent.putExtra("name" , fullName);
				intent.putExtra("phone" , nearList.get(position)._business_phone);
				intent.putExtra("color" , nearList.get(position)._color);
				intent.putExtra("logo" , nearList.get(position)._logo);
				
				startActivity(intent);
			}
			
		});
		
		
		connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		isOnline = networkInfo.isConnected();

		
		USERID = GlobalVariables.userId;
		
		locationManager = (LocationManager) ActivityParentMerchant.this.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);
		
		if(location != null)
		{
			double lng = location.getLongitude();
			double lat = location.getLatitude();
			
			latitude = String.valueOf(lat);
			longitude = String.valueOf(lng);
			
			Log.d("latitude inside if------->" , " : " + lat);
			Log.d("longitude inside if------->" , " : " + lng);
						
		}else
		{
			Log.d("No provider---------->" , " : ");
		}
		
		if(isOnline)
		{
			new GetNearByMerchant().execute(latitude , longitude , USERID);
		}else
		{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityParentMerchant.this);

			alertDialog.setIcon(R.drawable.alert_icon);
			alertDialog.setTitle("Internet Connection");
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

	public void updateButtonsDisplayOnClick()
	{
	
		buttonNearByMerchant.setBackgroundResource(R.drawable.appointment_button);
		buttonNearByShop.setBackgroundResource(R.drawable.queue_button);
		buttonMyRewards.setBackgroundResource(R.drawable.home_button);
		buttonUserProfile.setBackgroundResource(R.drawable.profile_button);
		
	}
	
	
	@Override
	public void onLocationChanged(Location location) 
	{
		double lng = location.getLongitude();
		double lat = location.getLatitude();
		
		latitude = String.valueOf(lat);
		longitude = String.valueOf(lng);
		
		Log.d("latitude onLocationChanged()------->" , " : " + lat);
		Log.d("longitude onLocationChanged()------->" , " : " + lng);
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		
	}


	/*****************class to get near by merchant******************/
	
	public class GetNearByMerchant extends AsyncTask<String , Boolean , Integer>
	{

		ProgressDialog progressDialog;
		
		
		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(ActivityParentMerchant.this);
			progressDialog.setTitle("Searching Near By Merchants...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();		
			
			super.onPreExecute();
		}
		

		

		@Override
		protected Integer doInBackground(String... params) 
		{
			nearList = MerchantManager.getUserNearByMerchant(latitude, longitude , USERID);
			
			return null;
		}
		

		@Override
		protected void onPostExecute(Integer result) 
		{
			progressDialog.dismiss();
			
			
			merchantAdapter = new MerchantAdapter(ActivityParentMerchant.this , 0, nearList);
			
			listViewNearByMerchant.setAdapter(merchantAdapter);
			
			
			
			super.onPostExecute(result);
		}
		
	}



}
