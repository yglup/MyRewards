package com.augurs.myrewards.manager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.augurs.myrewards.dataclasses.NearByMerchantData;
import com.augurs.myrewards.dataclasses.ResetPasswordData;
import com.augurs.myrewards.utilities.ConstantURLs;
import com.augurs.myrewards.utilities.GlobalVariables;

public class MerchantManager 
{
	static String jsonParameters = null;
	static String jsonResponse = null;
	
	/*************PASSWORD RESET FOR MY REWEARDS***************/

	public static List<NearByMerchantData> getUserNearByMerchant(String latitude , String longitude , String userId)
	{
		List<NearByMerchantData> nearList = new ArrayList<NearByMerchantData>();

		WebServiceManager webServiceManager = new WebServiceManager();

		jsonParameters = JsonManager.createJsonToGetNearByMerchant(latitude , longitude , userId);

		Log.d("json parameters------------->" , " : " + jsonParameters);

		webServiceManager.AddParam("json" , jsonParameters);
		webServiceManager.encodeUrl();

		jsonResponse = webServiceManager.callWebServiceAsyncWithJSONasPostData(ConstantURLs.baseUrL + "Post_NearByMerchant.php" , jsonParameters);

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
				nearList= ParserManager.parserToGetNearByMerchant(jsonResponse);
			}
		}catch(Exception e )
		{
			e.printStackTrace();
		}


		return nearList;
	}
}
