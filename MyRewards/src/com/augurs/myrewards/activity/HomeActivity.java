package com.augurs.myrewards.activity;


import com.augurs.myrewards.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;

public class HomeActivity extends FragmentActivity implements OnTabChangeListener//ActionBar.TabListener
{



	/*SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);


		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int position) 
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		Tab tab = actionBar.newTab().setText(getString(R.string.near_by).toUpperCase()).setTabListener(this);
		actionBar.addTab(tab);

		tab = actionBar.newTab().setText(getString(R.string.shop).toUpperCase()).setTabListener(this);
		actionBar.addTab(tab);

		tab = actionBar.newTab().setText(getString(R.string.my_profile).toUpperCase()).setTabListener(this);
		actionBar.addTab(tab);

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) 
		{
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{

	}*/


	/******************************************************************************/



	private FragmentTabHost mTabHost;



	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_tabs);
		// mTabHost = new FragmentTabHost(this);
		// mTabHost.setup(this, getSupportFragmentManager(),
		// R.id.menu_settings);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		Bundle b = new Bundle();
		b.putString("key", "Simple");
		mTabHost.addTab(mTabHost.newTabSpec("NearByMerchant").setIndicator("NearByMerchant"),
				NearByFragment.class, b);
		//
		b = new Bundle();
		System.out.print("hello git");
		b.putString("key", "Contacts");
		mTabHost.addTab(mTabHost.newTabSpec("Shop")
				.setIndicator("Shop!"), ShopFragment.class, b);
		b = new Bundle();
		b.putString("key", "Custom");
		mTabHost.addTab(mTabHost.newTabSpec("Update Profile").setIndicator("Update Profile"),
				UserProfileFragment.class, b);
		mTabHost.setOnTabChangedListener(this);
		// setContentView(mTabHost);

		for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
		{
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		//mTabHost.getTabWidget().setCurrentTab(0);
		mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#82CAFF"));


	}








	@Override
	public void onTabChanged(String tabId) 
	{
		if(tabId.equals("NearByMerchant"))
		{
			mTabHost.getTabWidget().setCurrentTab(0);

		}else if(tabId.equals("NearByMerchant"))
		{

		}else if(tabId.equals("NearByMerchant"))
		{

		}


		// TODO Auto-generated method stub
		for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
		{
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}

		mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#82CAFF"));
	}




}
