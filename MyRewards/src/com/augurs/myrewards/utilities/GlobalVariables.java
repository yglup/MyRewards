package com.augurs.myrewards.utilities;


import android.net.Uri;



//class to maintain global variables
public class GlobalVariables 
{

	public static boolean isErrorMessage = false;
	public static boolean isExceptionMessage = false;
	public static boolean islogginginAtFirst = true;
	public static boolean isApptypeSelected = true;
	public static boolean isLocationSelected = true;
	public static boolean isDateSelected = true;
	public static boolean isDoctorSelected = true;
	public static boolean appointment = false;
	public static boolean isReminderUpdatedSuccessfully = false;;
	public static String clientId = null;
	public static String httpStatusCode = null;
	
	public static String emailId = null;
	public static String userId = null;
	
	public static long calendarId = 0;
	//public static List<ErrorMessageData> errorMessageList;
	public static  String authenticationToken = null;
	public static String gcmAppRegistrationId = null;
	public static boolean checkIn = false;
	public static long _calendarEventID = 0;
	public static Uri _calendarUri;
	public static long _calendarIDBelowICS = 0;
	public static Uri _calendarUriBelowICS;
	
	public static String facebookAppId = null;
	public static String errorMessage = null;
	
	
	public GlobalVariables()
	{
		//errorMessageList = new ArrayList<ErrorMessageData>();
	}
}