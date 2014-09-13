package com.augurs.myrewards.activity;


import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import com.augurs.myrewards.R;
import com.augurs.myrewards.adapter.MerchantAdapter;
import com.augurs.myrewards.database.MyRewardsSqliteHelper;
import com.augurs.myrewards.database.MyRewardsUserId;
import com.augurs.myrewards.dataclasses.NearByMerchantData;
import com.augurs.myrewards.manager.MerchantManager;
import com.augurs.myrewards.utilities.GlobalVariables;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class NearByFragment extends Fragment
{

	List<NearByMerchantData> nearList = new ArrayList<NearByMerchantData>();
	MerchantAdapter merchantAdapter;
	
	
	
	// All static variables


	String logoUrl = null;
	String themeUrl = null;
	
	
	
	ListView list;
	
	String latitude = "18.5203";
	String longitude = "73.8567";
	
	String userId = null;
	String query = null;
	
	SQLiteDatabase database;
	MyRewardsSqliteHelper datahelper;
	MyRewardsUserId rewardsDatabase;
	String fullName = null;
	
	
	
	int color;
	int index = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) 
	{

		View view = inflater.inflate(R.layout.near_by_you , container , false );
		
		rewardsDatabase = new MyRewardsUserId(getActivity());
		datahelper = new MyRewardsSqliteHelper(getActivity());
		
		list = (ListView)view.findViewById(R.id.list);
		
		
		
		list.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent , View view , int position , long id) 
			{
				Log.d("position-------->" , " : " + position);
				
				fullName = nearList.get(position)._business_name + " " + nearList.get(position)._name;
				
				Intent intent = new Intent(getActivity() , MerchantDetailsActivity.class);
				intent.putExtra("name" , fullName);
				intent.putExtra("phone" , nearList.get(position)._business_phone);
				intent.putExtra("color" , nearList.get(position)._color);
				intent.putExtra("logo" , nearList.get(position)._logo);
				
				startActivity(intent);
			}
			
		});
		
		
		
		return view;
	}

	
	
	@Override
	public void onResume() 
	{
		super.onResume();
		Log.d("onResume()------->" , "onResume()");
		new GetNearByMerchant().execute(latitude , longitude);
		
		
		
		
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
	}

	public void updateAdapter()
	{
		merchantAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
				
		super.onCreate(savedInstanceState);

		
		
		/*rewardsDatabase = new MyRewardsUserId(getActivity());
		datahelper = new MyRewardsSqliteHelper(getActivity());
		
		rewardsDatabase.open();
		
		query = "select * from MyRewardsUserId where emailId = " + "'" + GlobalVariables.emailId + "'"; 
		Log.d("query---------->" , " : " + query);
		database = datahelper.openDB();
		
		Cursor cursor = database.rawQuery(query , null);
		
		
		while( cursor.moveToNext())
		{
			userId = cursor.getString(cursor.getColumnIndex("userId"));
		}
		
		Log.d("USER ID WITH DATABASE------->" , " : " + userId );
		
		cursor.close();
		rewardsDatabase.closeDB();
		datahelper.close();*/
		
		userId = GlobalVariables.userId;
		
		list = (ListView)getActivity().findViewById(R.id.list);
		
		new GetNearByMerchant().execute(latitude , longitude , userId);
		//new CustomListview().execute();

		

	}

	public class GetNearByMerchant extends AsyncTask<String , Boolean , Integer>
	{

		ProgressDialog progressDialog;
		
		
		@Override
		protected void onPreExecute() 
		{
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle("Searching Near By Merchants...");
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setMessage("Please wait!");
			progressDialog.show();		
			
			super.onPreExecute();
		}
		

		

		@Override
		protected Integer doInBackground(String... params) 
		{
			nearList = MerchantManager.getUserNearByMerchant(latitude, longitude , userId);
			
			return null;
		}
		

		@Override
		protected void onPostExecute(Integer result) 
		{
			progressDialog.dismiss();
			
			
			merchantAdapter = new MerchantAdapter(getActivity() , 0, nearList);
			
			list.setAdapter(merchantAdapter);
			updateAdapter();
			
			
			super.onPostExecute(result);
		}
		
	}
	

	


}
