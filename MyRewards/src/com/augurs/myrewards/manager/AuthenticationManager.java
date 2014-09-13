package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.augurs.myrewards.dataclasses.AuthenticateUserData;
import com.augurs.myrewards.utilities.ConstantURLs;
import com.augurs.myrewards.utilities.GlobalVariables;

public class AuthenticationManager 
{

	static String jsonParameters = null;
	static String jsonResponse = null;


	/**********USER AUTHENTICATION FOR MY REWARDS***********/

	public static List<AuthenticateUserData> getUserRegistration( String fbId , String emailId , String password )
	{
		List<AuthenticateUserData> authList = new ArrayList<AuthenticateUserData>();

		WebServiceManager webServiceManager = new WebServiceManager();

		jsonParameters = JsonManager.createJsonToGetAuthenticateUser(fbId, emailId, password);


		Log.d("json parameters------------->" , " : " + jsonParameters);

		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();

		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_AuthenticateUser.php" , jsonParameters);

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
				authList = ParserManager.parserToGetAuthenticateUser(jsonResponse);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return authList;

	}

}
