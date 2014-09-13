package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.augurs.myrewards.dataclasses.ResetPasswordData;
import com.augurs.myrewards.utilities.ConstantURLs;
import com.augurs.myrewards.utilities.GlobalVariables;

public class PasswordManager 
{
	static String jsonParameters = null;
	static String jsonResponse = null;



	/*************PASSWORD RESET FOR MY REWEARDS***************/

	public static List<ResetPasswordData> getUserPasswordDetails(String emailId)
	{
		List<ResetPasswordData> passList = new ArrayList<ResetPasswordData>();

		WebServiceManager webServiceManager = new WebServiceManager();

		jsonParameters = JsonManager.createJsonToGetPasswordDetails(emailId);

		Log.d("json parameters------------->" , " : " + jsonParameters);

		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();

		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_ResetsPassword.php" , jsonParameters);

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
				passList= ParserManager.parserToGetResetUserPassord(jsonResponse);
			}
		}catch(Exception e )
		{
			e.printStackTrace();
		}


		return passList;
	}
}
