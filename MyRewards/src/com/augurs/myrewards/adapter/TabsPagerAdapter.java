package com.augurs.myrewards.adapter;

import com.augurs.myrewards.activity.NearByFragment;
import com.augurs.myrewards.activity.ShopFragment;
import com.augurs.myrewards.activity.UserProfileFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new NearByFragment();
		case 1:
			// Games fragment activity
			return new ShopFragment();
		case 2:
			// Movies fragment activity
			return new UserProfileFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
