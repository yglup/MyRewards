package com.augurs.myrewards.dataclasses;

public class ResetPasswordData 
{
	public String _status;
	public String _message;
	public String _server_time_stamp;
	public String _userId;
	
	
	
	public ResetPasswordData()
	{
		_status = null;
		_message = null;
		_server_time_stamp = null;
		_userId = null;
	}



	public String get_status() 
	{
		return _status;
	}



	public void set_status(String _status)
	{
		this._status = _status;
	}



	public String get_message() 
	{
		return _message;
	}



	public void set_message(String _message) 
	{
		this._message = _message;
	}



	public String get_server_time_stamp()
	{
		return _server_time_stamp;
	}



	public void set_server_time_stamp(String _server_time_stamp) 
	{
		this._server_time_stamp = _server_time_stamp;
	}



	public String get_userId() 
	{
		return _userId;
	}



	public void set_userId(String _userId) 
	{
		this._userId = _userId;
	}
	
	
}
