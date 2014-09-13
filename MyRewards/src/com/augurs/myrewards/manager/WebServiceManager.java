package com.augurs.myrewards.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.augurs.myrewards.utilities.GlobalVariables;

import android.util.Log;

public class WebServiceManager 
{

	static UrlEncodedFormEntity formEntity;
	private static ArrayList<NameValuePair> params;
	static HttpResponse response;
	String statusCode = null;
	
	// Web service call for POST methods
	public String callWebServiceAsyncWithJSONasPostData(String url, String jsonParameters) 
	{
		String jsonText = null;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters , 0/*50000*/);
		HttpConnectionParams.setSoTimeout(httpParameters, /*5000*/0);
		
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		HttpPost httpPost = new HttpPost(url);
		try 
		{

			httpPost.setEntity(new StringEntity(jsonParameters, "UTF-8"));
			httpPost.addHeader("Content-Type", "application/json");
			response = client.execute(httpPost);

			int httpStatusCode = response.getStatusLine().getStatusCode();
			Log.d("Http Status Code------>",""+httpStatusCode);
			HttpEntity responseEntity = response.getEntity();
			jsonText = EntityUtils.toString(responseEntity);
			Log.d("json string------>",""+jsonText);
			Log.d("WEBSERVICE", "Pass");
			statusCode = Integer.toString(httpStatusCode);
			if(httpStatusCode == 200)
			{
				GlobalVariables.isErrorMessage = false;
				GlobalVariables.httpStatusCode = statusCode;
			} else if(httpStatusCode == 500 || jsonText == null)
			{
				GlobalVariables.httpStatusCode = statusCode;
				GlobalVariables.isErrorMessage = true;
				
			}/*else if(httpStatusCode == 500 || jsonText == null)
			{
				GlobalVariables.isErrorMessage = true;
			}*/else 
			{
				jsonText =  "";
				GlobalVariables.isErrorMessage = true;
			}

		}catch(SocketTimeoutException e)
		{
			e.printStackTrace();
			GlobalVariables.isExceptionMessage = true;
			jsonText = "Socket Time Out Exception";
			
		}catch(ConnectTimeoutException e)
		{
			Log.d("CONNECTION TIMEOUT EXCEPTION--->", e.getMessage().toString());
			GlobalVariables.isExceptionMessage = true;
			jsonText = "Connection Time Out Exception";
			//return jsonText;
		
		}catch (ClientProtocolException e)
		{
			Log.d("CLIENT PROTOCOL EXCEPTION---->" , e.getMessage().toString());
			//client.getConnectionManager().shutdown();
			GlobalVariables.isExceptionMessage = true;
			jsonText = "Client Protocol Exception";
			//return jsonText;
		
		}catch (IOException e)
		{
			Log.d("REST IO ERROR------>", "There was an IO Stream related error",e);
			//client.getConnectionManager().shutdown();
			GlobalVariables.isExceptionMessage = true;
			jsonText = "IO Exception";
			//return jsonText;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			GlobalVariables.isExceptionMessage = true;
		}

		return jsonText;

	}

	// Web Service call for GET methods
	public String callWebServiceAsyncGet(String url)
	{
		String jsonText = null;
		try
		{

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
			HttpConnectionParams.setSoTimeout(httpParameters, 10000);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);

			HttpGet put = new HttpGet(url);
			//put.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(put);
			int httpStatusCode = response.getStatusLine().getStatusCode();
			Log.d("Http Status Code",""+httpStatusCode);
			HttpEntity responseEntity = response.getEntity();
			jsonText = EntityUtils.toString(responseEntity);
			Log.d("json string --->",""+jsonText);
			Log.d("WEBSERVICE", "Pass");

			/*switch(httpStatusCode)
			{
				case 200:
					GlobalVariables.isErrorMessage = false;
					break;

				case 500:
					GlobalVariables.isErrorMessage = true;
					break;
			}*/

			if(httpStatusCode == 200)
			{
				GlobalVariables.isErrorMessage = false;
			}

			if(httpStatusCode == 500 || jsonText == null)
			{
				GlobalVariables.isErrorMessage = true;
			}

		} catch (ClientProtocolException e)
		{
			Log.d("REST", "There was a protocol based error", e);
			
		} catch (IOException e) 
		{
			Log.d("REST", "There was an IO Stream related error",e);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return jsonText;
	}

	// Adding json parameters
	public void AddParam(String name, String value) 
	{
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(name, value));
	}

	// Encoding  json parameters to URL
	public void encodeUrl()
	{
		try 
		{
			formEntity = new UrlEncodedFormEntity(params);
			
		} catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}



}
