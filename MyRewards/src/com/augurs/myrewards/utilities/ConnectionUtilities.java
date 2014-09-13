package com.augurs.myrewards.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionUtilities 
{
	public boolean isNetAvailable(Context context)
	{


		boolean isNetAvailable=false;

		if ( context != null )
		{

			ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			if ( mgr != null )
			{
				boolean mobileNetwork = false;
				boolean wifiNetwork = false;
				boolean wiMaxNetwork = false;

				boolean mobileNetworkConnecetd = false;
				boolean wifiNetworkConnecetd = false;
				boolean wiMaxNetworkConnected = false;

				NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo wiMaxInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

				if ( mobileInfo != null )
					mobileNetwork = mobileInfo.isAvailable();

				if ( wifiInfo != null )
					wifiNetwork = wifiInfo.isAvailable();

				if(wiMaxInfo != null)
					wiMaxNetwork = wiMaxInfo.isAvailable();


				if(wifiNetwork == true || mobileNetwork == true || wiMaxNetwork == true)
				{
					mobileNetworkConnecetd = mobileInfo.isConnectedOrConnecting();
					wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting();
					try 
					{
						wiMaxNetworkConnected = wiMaxInfo.isConnectedOrConnecting();
					} catch (Exception e) 
					{
						Log.e("Exception in wifi----->" , " : " + e.getMessage());
					}

				}

				// If any one of connected means returns true..
				isNetAvailable = ( mobileNetworkConnecetd || wifiNetworkConnecetd || wiMaxNetworkConnected );
			}
		}
		return isNetAvailable;
	}


}
