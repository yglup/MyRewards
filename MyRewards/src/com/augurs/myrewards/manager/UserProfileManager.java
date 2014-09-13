package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.augurs.myrewards.dataclasses.RegisterUserData;
import com.augurs.myrewards.dataclasses.UserProfileInfo;
import com.augurs.myrewards.dataclasses.UserUpdateProfileInfo;
import com.augurs.myrewards.utilities.ConstantURLs;
import com.augurs.myrewards.utilities.GlobalVariables;

public class UserProfileManager 
{
	static String jsonParameters = null;
	static String jsonResponse = null;


	/*****************GET USER PROFILE DETAILS***************/


	public static List<UserProfileInfo> getUserProfileInfo(String userId)
	{
		List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();

		WebServiceManager webServiceManager = new WebServiceManager();

		jsonParameters = JsonManager.createJsonToGetUserProfileInfo(userId);


		Log.d("json parameters------------->" , " : " + jsonParameters);

		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();

		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_UserProfile.php" , jsonParameters);

		try
		{
			if( GlobalVariables.isErrorMessage == true)
			{
				//Error Message will be shown here

			}else if( GlobalVariables.isExceptionMessage == true)
			{
				//Error message will be shown here
			}else
			{
				userInfoList = ParserManager.parserToGetUserInfo(jsonResponse);
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return userInfoList;
	}

	
	/*************UPDATE USER PROFILE*****************/

	public static List<UserUpdateProfileInfo> getUpdateUserProfileInfo(String userId , String firstName , String lastName , String udid , String source , String age , String gender , String address , String country , String state , String city , String zipCode , String aboutMe)
	{
		List<UserUpdateProfileInfo> userInfoList = new ArrayList<UserUpdateProfileInfo>();

		WebServiceManager webServiceManager = new WebServiceManager();

		jsonParameters = JsonManager.createJsonToGetUpdateuserProfileInfo(userId , firstName , lastName , udid , source ,age , gender , address , country , state , city , zipCode , aboutMe);


		Log.d("json parameters------------->" , " : " + jsonParameters);

		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();

		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_UserProfile.php" , jsonParameters);

		try
		{
			if( GlobalVariables.isErrorMessage == true)
			{
				//Error Message will be shown here

			}else if( GlobalVariables.isExceptionMessage == true)
			{
				//Error message will be shown here
			}else
			{
				userInfoList = ParserManager.parserToGetUserUpdateProfileInfo(jsonResponse);
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return userInfoList;
	}






	/*************UPLOAD USER PROFILE PHOTO************/




}
