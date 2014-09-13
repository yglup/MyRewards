package com.augurs.myrewards.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.augurs.myrewards.R;
import com.augurs.myrewards.classes.ImageLoader;
import com.augurs.myrewards.dataclasses.NearByMerchantData;

public class MerchantAdapter extends BaseAdapter
{
	List<NearByMerchantData> nearList;
	Context context;
	ImageLoader imageLoader;
	boolean isMember = true;

	String merchantColor = null;
	
	public MerchantAdapter( Context context, int textViewResourceId, List<NearByMerchantData> nearData)
	{
		this.context = context;
		this.nearList = nearData;
		imageLoader = new ImageLoader(context);
	}


	@Override
	public int getCount() 
	{
		return nearList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return nearList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(final int position , View convertView , ViewGroup parent) 
	{
		View view = convertView;
		final Holder holder;
		if( view == null)
		{
			holder = new Holder();

			LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_row_merchant , null);

			holder.textViewName = (TextView) view.findViewById(R.id.name);
			holder.textViewPhone = (TextView) view.findViewById(R.id.timestamp);
			holder.textViewBusinessAddress = (TextView) view.findViewById(R.id.txtStatusMsg);
			holder.textViewDistance = (TextView) view.findViewById(R.id.textViewMerchantDistance);
			
			holder.imageViewMap = (ImageView) view.findViewById(R.id.imageViewMarker);
			holder.imageViewLogo = (ImageView) view.findViewById(R.id.profilePic);
			holder.imageViewTheme = (ImageView) view.findViewById(R.id.feedImage1);
			holder.buttonAddMember = (Button) view.findViewById(R.id.buttonAddMember);

			
			holder.textViewName.setText(nearList.get(position)._business_name + " " + nearList.get(position)._name);
			holder.textViewPhone.setText(nearList.get(position)._business_phone);
			holder.textViewBusinessAddress.setText(nearList.get(position)._business_address);
			holder.textViewDistance.setText(nearList.get(position)._distance.substring(0, 5) + "mtrs");
			merchantColor = nearList.get(position)._color;
			merchantColor = "#" + merchantColor;
			holder.imageViewTheme.setBackgroundColor(Color.parseColor(merchantColor));
			//imageLoader.DisplayImage(nearList.get(position)._logo , holder.imageViewLogo);
			//imageLoader.DisplayImage(nearList.get(position)._theme , holder.imageViewTheme);

			new GetBitmapForLogo().execute(view , nearList.get(position)._logo , holder.imageViewLogo);
			//new GetBitmapForTheme().execute(view , nearList.get(position)._theme , holder.imageViewTheme);

			view.setTag(holder);
		}else
		{
			holder = (Holder) view.getTag();
			holder.textViewName.setText(nearList.get(position)._business_name + " " + nearList.get(position)._name);
			holder.textViewPhone.setText(nearList.get(position)._business_phone);
			holder.textViewBusinessAddress.setText(nearList.get(position)._business_address);
			holder.textViewDistance.setText(nearList.get(position)._distance.substring(0, 5) + "mtrs");
			
			merchantColor = nearList.get(position)._color;
			merchantColor = "#" + merchantColor;
			holder.imageViewTheme.setBackgroundColor(Color.parseColor(merchantColor));
			
			//new GetBitmapForLogo().execute(view , nearList.get(position)._logo , holder.imageViewLogo);
			
		}

		if(isMember)
		{
			holder.buttonAddMember.setOnClickListener(new OnClickListener() 

			{

				@Override
				public void onClick(View view)
				{
					Log.d("button OnClick()POSITION-------->" , ":" + position);
					Log.d("button OnClick()-------->" , ":");

					holder.buttonAddMember.setBackgroundResource(R.drawable.check_member);
				}
			});
		}
		return view;
	}


	/*************LOAD BITMAP FROM URL FOR LOGO******************/

	public class GetBitmapForLogo extends AsyncTask<Object , String , Bitmap>
	{
		Bitmap bitmap = null;
		View view;
		ImageView imgYoutube;
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}


		@Override
		protected Bitmap doInBackground(Object... params) 
		{
			try 
			{
				view = (View) params[0];

				String newUrl = (String)(params[1]);

				imgYoutube = (ImageView) params[2];
				Log.d("BITMAP URL---->" , newUrl);
				URL url = new URL(newUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Config.RGB_565;
				bitmap = BitmapFactory.decodeStream(input, null, options);				

			}catch(IOException e)
			{
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) 
		{
			if (bitmap != null && view != null) 
			{
				imgYoutube.setImageBitmap(bitmap);
			}



			super.onPostExecute(bitmap);

		}

	}


	/*************LOAD BITMAP FROM URL FOR THEME******************/

	public class GetBitmapForTheme extends AsyncTask<Object , String , Bitmap>
	{
		Bitmap bitmap = null;
		View view;
		ImageView imgYoutube;
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}


		@Override
		protected Bitmap doInBackground(Object... params) 
		{
			try 
			{
				view = (View) params[0];

				String newUrl = (String)(params[1]);

				imgYoutube = (ImageView) params[2];
				Log.d("BITMAP URL---->" , newUrl);
				URL url = new URL(newUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				bitmap = BitmapFactory.decodeStream(input);
				//bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

				

			}catch(IOException e)
			{
				e.printStackTrace();
			}
			return bitmap;
		}


		@Override
		protected void onPostExecute(Bitmap bitmap) 
		{
			if (bitmap != null && view != null) 
			{
				
				imgYoutube.setImageBitmap(bitmap);
			}


			super.onPostExecute(bitmap);

		}
	}



	public static class Holder
	{
		TextView textViewName;
		TextView textViewPhone;
		TextView textViewBusinessAddress;
		TextView textViewDistance;
		
		ImageView imageViewLogo;
		ImageView imageViewTheme;
		ImageView imageViewMap;
		
		Button buttonAddMember;
	}

	/****************GET ROUNDED BITMAP*******************/

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) 
	{
		Bitmap roundedBitmap = null;
		try 
		{
			roundedBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(roundedBitmap);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(50, 50, 50, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			//imgYoutube.setImageBitmap(bitmap);
		} catch (NullPointerException e) 
		{
			
		} catch (OutOfMemoryError o) 
		{
			
		}
		return roundedBitmap;
	}




}
