package com.augurs.myrewards.activity;

import java.util.ArrayList;
import java.util.List;

import com.augurs.myrewards.R;
import com.augurs.myrewards.dataclasses.UserProfileInfo;
import com.augurs.myrewards.dataclasses.UserUpdateProfileInfo;
import com.augurs.myrewards.manager.UserProfileManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileFragment extends Fragment 
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


	Button buttonUpdateProfile;
	EditText editTextFirstname , editTextEmailId , editTextGender , editTextAddress , editTextPhone;

	List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();
	List<UserUpdateProfileInfo> userUpdateList = new ArrayList<UserUpdateProfileInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.activity_update_profile , container , false );

		editTextFirstname = (EditText) view.findViewById(R.id.editText_update_firstname);
		editTextEmailId = (EditText) view.findViewById(R.id.editText_update_email);
		editTextGender = (EditText) view.findViewById(R.id.editText_update_gender);
		editTextAddress = (EditText) view.findViewById(R.id.editText_update_address);
		editTextPhone = (EditText) view.findViewById(R.id.editText_update_phone);

		buttonUpdateProfile = (Button) view.findViewById(R.id.button_update_profile);


		buttonUpdateProfile.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View view) 
			{
				updateFirstName = editTextFirstname.getText().toString();
				updateEmailId = editTextEmailId.getText().toString();
				updateAddress = editTextAddress.getText().toString();
				updateGender = editTextGender.getText().toString();
				updatePhone = editTextPhone.getText().toString();

				new GetUserUpdateProfileInfo().execute();
			}
		});

		return view;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		userId = "11";//for the time being userId = 11

		/*buttonUpdateProfile.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View view) 
			{
				updateFirstName = editTextFirstname.getText().toString();
				updateEmailId = editTextEmailId.getText().toString();
				updateAddress = editTextAddress.getText().toString();
				updateGender = editTextGender.getText().toString();
				updatePhone = editTextPhone.getText().toString();

				new GetUserUpdateProfileInfo().execute();
			}
		});*/

		new GetUserProfileInfo().execute(userId);
	}


	/**********CLASS TO GET USER PROFILE INFO**********/

	public class GetUserProfileInfo extends AsyncTask<String , Boolean , Integer>
	{

		ProgressDialog progressDialog;
		int index = 0;


		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();

			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle("Fetching Profile Info");
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Please wait !");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... params)
		{
			//userId = "11";
			userInfoList = UserProfileManager.getUserProfileInfo(userId);

			return null;
		}


		@Override
		protected void onPostExecute(Integer result) 
		{

			super.onPostExecute(result);

			editTextFirstname.setText(userInfoList.get(index)._firstName);
			editTextEmailId.setText(userInfoList.get(index)._emailId);
			//editTextAddress.setText(userInfoList.get(index)._address + "," + userInfoList.get(index)._state);
			editTextGender.setText(userInfoList.get(index)._gender);


			UserProfileFragment.this.getActivity().setTitle(userInfoList.get(index)._firstName + " " + userInfoList.get(index)._lastName);

			progressDialog.dismiss();
		}
	}


	/**********CLASS TO GET USER PROFILE INFO**********/

	public class GetUserUpdateProfileInfo extends AsyncTask<String , Boolean , Integer>
	{

		ProgressDialog progressDialog;
		int index = 0;


		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();

			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle("Updating Profile Info");
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Please wait !");
			progressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... params)
		{
			//userId = "11";
			userUpdateList = UserProfileManager.getUpdateUserProfileInfo(userId, updateFirstName , lastName , udid, source, age, updateGender, updateAddress, country, state, city, zipCode, aboutMe);


			return null;
		}


		@Override
		protected void onPostExecute(Integer result) 
		{		

			editTextFirstname.setText(userInfoList.get(index)._firstName);
			editTextEmailId.setText(userInfoList.get(index)._emailId);
			//editTextAddress.setText(userInfoList.get(index)._address + "," + userInfoList.get(index)._state);
			editTextGender.setText(userInfoList.get(index)._gender);


			UserProfileFragment.this.getActivity().setTitle(userInfoList.get(index)._firstName + " " + userInfoList.get(index)._lastName);

			progressDialog.dismiss();
			super.onPostExecute(result);
			
			message = userUpdateList.get(index)._message;
			status = userUpdateList.get(index)._status;
			Log.d("STATUS------->" , " : " + status);
			Log.d("MESSAGE------->" , " : " + message);
			
			if(status.equals("SUCCESS"))
			{
				
				Toast.makeText(UserProfileFragment.this.getActivity() , "Updated Successfully " , Toast.LENGTH_LONG).show();				
				startActivity(new Intent(UserProfileFragment.this.getActivity() , LoginActivity.class));
			}else if(status.equals("FAILED"))
			{
				Toast.makeText(UserProfileFragment.this.getActivity() , "Unable to Update " , Toast.LENGTH_LONG).show();
			}
		}
	}
}
