package com.augurs.myrewards.manager;

import org.json.JSONObject;

import android.util.Log;

public class JsonManager 
{
	static String message = null;


	/**********CREATE JSON OF REGISTER USER*************/

	public static String createJsonToGetRegisterUserData(String merchantId , String firstName , String emailId , String lastName , String password , String fbId , String udId , String source , String age , String gender , String address , String country , String state , String city , String zipCode , String aboutMe)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObject = new JSONObject();

		JSONObject Obj = new JSONObject();

		try
		{
			jsonObject.put("firstName" , firstName);
			jsonObject.put("lastName" , lastName);
			jsonObject.put("emailId" , emailId);
			jsonObject.put("password" , password);
			jsonObject.put("fbId" , fbId);
			jsonObject.put("udid", udId);
			jsonObject.put("source" , source);
			jsonObject.put("age" , age);
			jsonObject.put("gender" , gender);
			jsonObject.put("address" , address);
			jsonObject.put("country" , country);
			jsonObject.put("state" , state);
			jsonObject.put("city" , city);
			jsonObject.put("zipCode" , zipCode);
			jsonObject.put("aboutMe" , aboutMe);

			ATObject.put("user_details" , jsonObject);
			ATObject.put("merchantId" , merchantId);

			Obj.put("AT", ATObject);

			message = Obj.toString();

		}catch(Exception e)
		{
			Log.e("Json Exception in user registration----->" , " : " + e.getMessage());
		}


		return message;
	}



	/**********CREATE JSON OF AUTHENTICATE USER***************/

	public static String createJsonToGetAuthenticateUser(String fbId , String emailId , String password)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObj = new JSONObject();
		try
		{
			jsonObject.put("fbId" , fbId);
			jsonObject.put("emailId" , emailId);
			jsonObject.put("password" , password);

			ATObj.put("AT" , jsonObject);

			message = ATObj.toString();
			Log.d("Json Message----------->" , " : " + message);
		}catch(Exception e)
		{
			Log.e("Json Exception in user authentication----->" , " : " + e.getMessage());
		}


		return message;
	}

	/*************CREATE JSON TO GET USER PASSWORD***************/

	public static String createJsonToGetPasswordDetails(String emailId)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObject = new JSONObject();

		try
		{
			jsonObject.put("emailId" , emailId);

			ATObject.put("AT" , jsonObject);

			message = ATObject.toString();

		}catch(Exception e)
		{
			Log.e("Json Exception in user resetting password----->" , " : " + e.getMessage());
		}

		return message;
	}

	/*************CREATE JSON TO GET USER PROFILE INFO***************/

	public static String createJsonToGetUserProfileInfo(String userId)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObject = new JSONObject();

		try
		{
			jsonObject.put("userId" , userId);

			ATObject.put("AT" , jsonObject);

			message = ATObject.toString();

		}catch(Exception e)
		{
			Log.e("Json Exception in user profile info----->" , " : " + e.getMessage());
		}

		return message;
	}
	
	/**********CREATE JSON TO GET UPDATE USER PROFILE INFO**********/
	
	public static String createJsonToGetUpdateuserProfileInfo(String userId , String firstName , String lastName , String udid , String source , String age , String gender , String address , String country , String state , String city , String zipCode , String aboutMe)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObject = new JSONObject();
		JSONObject Obj = new JSONObject();
		
		try
		{
			jsonObject.put("firstName", firstName);
			jsonObject.put("lastName" , lastName);
			jsonObject.put("udid" , udid);
			jsonObject.put("source" , source);
			jsonObject.put("age" , age);
			jsonObject.put("gender" , gender);
			jsonObject.put("address" , address);
			jsonObject.put("country", country);
			jsonObject.put("state" , state);
			jsonObject.put("city" , city);
			jsonObject.put("zipCode" , zipCode);
			jsonObject.put("aboutMe" , aboutMe);
			
			ATObject.put("user_details" , jsonObject);
			ATObject.put("userId" , userId);
			
			Obj.put("AT" , ATObject);
			
			message = Obj.toString();
			
			
		}catch(Exception e)
		{
			Log.e("Json Exception in user update profile info----->" , " : " + e.getMessage());
		}
		
		return message;
	}
	
	
	public static String createJsonToGetNearByMerchant(String latitude , String longitude , String userId)
	{
		JSONObject jsonObject = new JSONObject();
		JSONObject ATObject = new JSONObject();

		try
		{
			jsonObject.put("latitude" , latitude);
			jsonObject.put("longitude" , longitude);
			jsonObject.put("userId" , userId);
			
			ATObject.put("AT" , jsonObject);

			message = ATObject.toString();

		}catch(Exception e)
		{
			Log.e("Json Exception in user near by merchant----->" , " : " + e.getMessage());
		}
 
		return message;
	}
}
