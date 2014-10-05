package com.cangdao.yellowpages;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.widget.SearchView;

public class MainActivity extends SherlockFragmentActivity implements SearchView.OnQueryTextListener {

	private static final String TAG = "MainActivity";
	
	private ActionBar mActionBar;  
	private ViewPager  mViewPager;
	private TabsAdapter mTabsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabs_pager);
        
        mActionBar = getSupportActionBar();  
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        mViewPager = (ViewPager)findViewById(R.id.pager);
        
        mTabsAdapter = new TabsAdapter(this, mActionBar, mViewPager);
        
        initTabs();
    }

    private void initTabs() {
    	String[] categories = getResources().getStringArray(R.array.tab_categories);
    	for (String category : categories) {
    		mTabsAdapter.addTab(category, DemoFragment.class, null);
    	}
    }
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------- LifeCycle Override ---------------------------------------
    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu, menu);
        SearchView sv = (SearchView)menu.findItem(R.id.action_search).getActionView();
//        sv.setQueryHint(getString(2131361871));
//        sv.setIconifiedByDefault(true);
        sv.setOnQueryTextListener(this);
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_about :
    		//TODO: Start About Activity
    		
    		break;
    		
    	case R.id.action_upgrade :
    		//TODO: Start Version Checking
    		
    		break;
    	}
    	return true;
    }
    
    //----------------------------------------------------------------------------------------------
	//--------------------------------- Interface Implementations ----------------------------------

	//----------- Impl for SearchView.OnQueryTextListener -----------
	@Override
	public boolean onQueryTextChange(String arg0) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}

    //----------------------------------------------------------------------------------------------
	//---------------------------------------- Inner Classes ---------------------------------------
	public static class TabsAdapter extends FragmentPagerAdapter implements
			ViewPager.OnPageChangeListener, ActionBar.TabListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(FragmentActivity activity, ActionBar actionBar, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = actionBar;
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(String category, Class<?> clss, Bundle args) {
			
    		Tab tab = mActionBar.newTab();
    		tab.setText(category);
    		tab.setTabListener(this);
    		mActionBar.addTab(tab);

			TabInfo info = new TabInfo(category, clss, args);
			mTabs.add(info);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		}

		//----------- Impl for ViewPager.OnPageChangeListener -----------
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
		
		//----------- Impl for ActionBar.TabListener -----------
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mViewPager.setCurrentItem(tab.getPosition(), true);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}
	}
	
	public static class DemoFragment extends Fragment {

		public DemoFragment() {
			super();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_main, null);
		}
		
	}
}
