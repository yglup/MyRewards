package com.augurs.myrewards.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurs.myrewards.R;

public class MerchantDetailsActivity extends Activity 
{
	
	String merchantName = null;
	String merchantPhone = null;
	String merchantColor = null;
	String merchantLogo = null;
	
	RelativeLayout relativeLayout;
	
	ImageView imageViewLogo;
	TextView textViewName;
	TextView textViewPhone;
	
	Button buttonShare;
	
	Resources res;
	
	int color;
	
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchantdetails);
		
		intent = getIntent();
		
		merchantName = intent.getStringExtra("name");
		merchantPhone = intent.getStringExtra("phone");
		merchantColor = intent.getStringExtra("color");
		merchantLogo = intent.getStringExtra("logo");
		
		merchantColor = "#" + merchantColor;
		
		//color = Integer.parseInt(merchantColor);
		res = getResources();
		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMerchantColor);
		imageViewLogo = (ImageView) findViewById(R.id.imageViewMerchantDetailLogoImage);
		
		textViewName = (TextView) findViewById(R.id.textViewMerchantName);
		textViewPhone = (TextView) findViewById(R.id.textViewMerchantPhone);
		
		buttonShare = (Button) findViewById(R.id.buttonMerchantShare);
		
		
		relativeLayout.setBackgroundColor(Color.parseColor(merchantColor));
		
		
		
		new GetBitmapForLogo().execute();
		
		buttonShare.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View view)
			{
				
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			
			}
		});
		
	}
	
	

	/*************LOAD BITMAP FROM URL FOR LOGO******************/

	public class GetBitmapForLogo extends AsyncTask<Void , String , Bitmap>
	{
		Bitmap bitmap = null;
		
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}


		@Override
		protected Bitmap doInBackground(Void... arg0) 
		{
			try 
			{
				
				
				URL url = new URL(merchantLogo);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Config.RGB_565;
				bitmap = BitmapFactory.decodeStream(input, null, options);
				//bitmap = BitmapFactory.decodeStream(input);
				//bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

				//imageView.setImageBitmap(bitmap);

			}catch(IOException e)
			{
				e.printStackTrace();
			}
			return bitmap;
		}


		@Override
		protected void onPostExecute(Bitmap bitmap) 
		{
			if (bitmap != null) 
			{
				//ImageView albumArt = (ImageView) view.findViewById(R.id.imageViewThumbnail);
				imageViewLogo.setImageBitmap(bitmap);
			}

			textViewName.setText(merchantName);
			textViewPhone.setText(merchantPhone);
			
			//relativeLayout.setBackground(res.getString(id));

			super.onPostExecute(bitmap);

		}


		



	}
}
