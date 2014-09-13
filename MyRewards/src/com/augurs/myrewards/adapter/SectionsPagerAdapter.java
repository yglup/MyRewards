package com.augurs.myrewards.adapter;


import com.augurs.myrewards.activity.NearByFragment;
import com.augurs.myrewards.activity.ShopFragment;
import com.augurs.myrewards.activity.UserProfileFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter 
{
	
	

	final int PAGE_COUNT = 3;

	public SectionsPagerAdapter(FragmentManager fm) 
	{
		super(fm);
		
		
	}

	@Override
	public Fragment getItem(int index) 
	{
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		switch(index)
		{
			case 0 : return new NearByFragment();
			
			case 1 : return new ShopFragment();
			
			case 2 : return new UserProfileFragment(); 
		}
	
		
		return null;
	
	}

	@Override
	public int getCount() 
	{
		// Show 3 total pages.
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) 
	{
		
		
		switch (position) 
		{
			
			case 0 : return null;
		}
		
		return null;
	}
}
