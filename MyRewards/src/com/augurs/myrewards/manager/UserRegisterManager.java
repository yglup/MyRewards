package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.augurs.myrewards.dataclasses.RegisterUserData;
import com.augurs.myrewards.utilities.ConstantURLs;
import com.augurs.myrewards.utilities.GlobalVariables;

public class UserRegisterManager 
{
	static String jsonParameters = null;
	static String jsonResponse = null;
	
	
	/**********USER REGISTRATION FOR MY REWARDS**********/
	
	public static List<RegisterUserData> getUserRegistration( String merchantId , String firstName , String emailId , String lastName , String password , String fbId , String udId , String source , String age , String gender , String address , String country , String state , String city , String zipCode , String aboutMe )
	{
		List<RegisterUserData> registerList = new ArrayList<RegisterUserData>();
		
		WebServiceManager webServiceManager = new WebServiceManager();
		
		jsonParameters = JsonManager.createJsonToGetRegisterUserData(merchantId, firstName, emailId, lastName, password, fbId, udId, source, age, gender, address, country, state, city, zipCode, aboutMe);
		
		
		Log.d("json parameters------------->" , " : " + jsonParameters);
		
		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();
		
		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_RegisterUser.php" , jsonParameters);
		
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
				registerList = ParserManager.parserToGetRegisterUser(jsonResponse);
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return registerList;
		
	}
}
