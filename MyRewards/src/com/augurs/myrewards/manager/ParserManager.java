package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.augurs.myrewards.dataclasses.AuthenticateUserData;
import com.augurs.myrewards.dataclasses.NearByMerchantData;
import com.augurs.myrewards.dataclasses.RegisterUserData;
import com.augurs.myrewards.dataclasses.ResetPasswordData;
import com.augurs.myrewards.dataclasses.UserProfileInfo;
import com.augurs.myrewards.dataclasses.UserUpdateProfileInfo;



public class ParserManager 
{

	/**********PARSER TO GET AUTHENTICATE USER***************/

	public static List<AuthenticateUserData> parserToGetAuthenticateUser(String jString) throws JSONException
	{
		List<AuthenticateUserData> authList = new ArrayList<AuthenticateUserData>();

		try
		{
			AuthenticateUserData authData = new AuthenticateUserData();

			JSONObject jsonObject = new JSONObject(jString);

			authData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
			authData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
			
			if( authData._status.equals("SUCCESS"))
			{
				authData._userId = jsonObject.getJSONObject("AT").getString("userId");
			}
						
			authData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");

			authList.add(authData);


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return authList;
	}


	/*********PARSER TO GET USER REGISTRATION**************/

	public static List<RegisterUserData> parserToGetRegisterUser(String jString) throws JSONException
	{
		List<RegisterUserData> registerList = new ArrayList<RegisterUserData>();

		JSONObject jsonObject = new JSONObject(jString);
		
		try
		{
			RegisterUserData registerData = new RegisterUserData();

			

			registerData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
			registerData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
			registerData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");
			
			registerData._userId = jsonObject.getJSONObject("AT").getString("userId");
			
			registerList.add(registerData);


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return registerList;
	}

	/************PARSER TO GET RESET PASSOWRD*************/

	public static List<ResetPasswordData> parserToGetResetUserPassord(String jString) throws JSONException
	{

		List<ResetPasswordData> resetPassList = new ArrayList<ResetPasswordData>();

		try
		{
			ResetPasswordData resetPassData = new ResetPasswordData();

			JSONObject jsonObject = new JSONObject(jString);

			resetPassData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
			resetPassData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
			resetPassData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");

			resetPassList.add(resetPassData);


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return resetPassList;
	}
	
	/*********PARSER TO GET USER PROFILE INFO**************/

	public static List<UserProfileInfo> parserToGetUserInfo(String jString) throws JSONException
	{
		List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();

		try
		{
			UserProfileInfo userInfoData = new UserProfileInfo();

			JSONObject jsonObject = new JSONObject(jString);

			userInfoData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
			userInfoData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
			//userInfoData._userId = jsonObject.getJSONObject("AT").getString("userId");
			userInfoData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");

			userInfoData._userId = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("id");
			userInfoData._merchantId = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("merchantId");
			userInfoData._firstName = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("firstName");
			userInfoData._lastName = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("lastName");
			userInfoData._emailId = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("emailId");
			userInfoData._fbId = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("fbId");
			userInfoData._udid = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("udid");
			userInfoData._source = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("source");
			userInfoData._age = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("age");
			userInfoData._gender = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("gender");
			userInfoData._address = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("address");
			userInfoData._country = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("country");
			userInfoData._state = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("state");
			userInfoData._city = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("city");
			userInfoData._zipCode = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("zipCode");
			userInfoData._aboutMe = jsonObject.getJSONObject("AT").getJSONObject("userDetails").getString("aboutMe");
			
			
			userInfoList.add(userInfoData);


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return userInfoList;
	}

	
	/********PARSER TO UPDATE USER PROFILE INFO**********/

	public static List<UserUpdateProfileInfo> parserToGetUserUpdateProfileInfo(String jString) throws JSONException
	{

		List<UserUpdateProfileInfo> updateProfileList = new ArrayList<UserUpdateProfileInfo>();

		try
		{
			UserUpdateProfileInfo updateProfileData = new UserUpdateProfileInfo();

			JSONObject jsonObject = new JSONObject(jString);

			updateProfileData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
			updateProfileData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
			updateProfileData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");

			updateProfileList.add(updateProfileData);


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return updateProfileList;
	}
	
	/************PARSER TO GET NEAR BY MERCHANT*************/

	public static List<NearByMerchantData> parserToGetNearByMerchant(String jString) throws JSONException
	{

		List<NearByMerchantData> nearList = new ArrayList<NearByMerchantData>();
		JSONArray jsonArray;
		try
		{
			JSONObject jsonObject = new JSONObject(jString);
			//resetPassData._server_time_stamp = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("server_time_stamp");
			jsonArray = jsonObject.getJSONObject("AT").getJSONArray("NearByMerchant");
			
			for( int i = 0 ; i < jsonArray.length() ; i++)
			{
				NearByMerchantData nearData = new NearByMerchantData();

				

				nearData._status = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("STATUS");
				nearData._message = jsonObject.getJSONObject("AT").getJSONObject("STATUS").getString("MESSAGE");
				
				JSONObject object = jsonArray.getJSONObject(i);
				
				nearData._id = object.getString("id");
				nearData._email = object.getString("email");
				//nearData._password = object.getString("password");
				nearData._business_name = object.getString("business_name");
				nearData._name = object.getString("name");
				//nearData._phone = object.getString("phone");
				nearData._business_address = object.getString("business_address");
				nearData._business_type = object.getString("business_type");
				nearData._business_phone = object.getString("business_phone");
				nearData._color = object.getString("color");
				nearData._theme = object.getString("theme");
				nearData._logo = object.getString("logo");
				nearData._latitude = object.getString("latitude");
				nearData._longitude = object.getString("longitude");
				nearData._product_ratings = object.getString("product_ratings");
				nearData._connect_facebook = object.getString("connect_facebook");
				nearData._connect_google = object.getString("connect_google");
				nearData._facebook_url = object.getString("facebook_url");
				nearData._google_url = object.getString("google_url");
				nearData._auto_check_in = object.getString("auto_check_in");
				nearData._distance = object.getString("distance");
				
				
				nearList.add(nearData);
			}
			


		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return nearList;
	}
}
