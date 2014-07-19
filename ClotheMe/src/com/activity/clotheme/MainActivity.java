package com.activity.clotheme;



import com.activity.adapter.ViewPagerAdapter;
import com.common.clothe.CommonDefine;
import com.common.clothe.CommonDefine.TabState;
import com.common.clothe.Log;
import com.daogen.clotheme.CategoryArchive;
import com.daogen.clotheme.CategoryArchiveDao;
import com.daogen.clotheme.DaoMaster;
import com.daogen.clotheme.DaoMaster.DevOpenHelper;
import com.daogen.clotheme.DaoSession;
import com.example.clotheme.R;
import com.logicalModelLayer.Implements.CategoryArchiveInfo;
import com.logicalModelLayer.Implements.CategoryInfo;
import com.logicalModelLayer.Implements.MeterialInfo;
import com.logicalModelLayer.Implements.PersonInfo;
import com.logicalModelLayer.Implements.SeasonInfo;
import com.logicalModelLayer.Implements.StorageLocationInfo;
import com.logicalModelLayer.Implements.StyleInfo;
import com.logicalModelLayer.Implements.ThicknessInfo;
import com.logicalModelLayer.Implements.WearPlaceInfo;


import java.util.ArrayList;  

import android.os.Bundle;  
import android.support.v4.app.FragmentActivity;  
import android.support.v4.app.FragmentManager;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.app.ActionBar;  
import android.app.ActionBar.Tab;  
import android.app.ActionBar.TabListener;  
import android.app.Activity;  
import android.app.FragmentTransaction;  
import android.view.LayoutInflater;  
import android.view.View;  

public class MainActivity extends FragmentActivity implements TabListener,OnPageChangeListener {  
      
    private ActionBar mActionBar;  
    private ViewPager mViewPager;  
    private ViewPagerAdapter mAdapter;  
    private ArrayList<View> mViews;  
    private ArrayList<ActionBar.Tab> mTabs;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.tab_view_pager);  
        //取得ActionBar  
        mActionBar=getActionBar();  
        //以Tab方式导航  
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        //禁用ActionBar标题  
//        mActionBar.setDisplayShowTitleEnabled(false);  
        //禁用ActionBar图标  
//        mActionBar.setDisplayUseLogoEnabled(false);  
        //禁用ActionBar返回键  
//        mActionBar.setDisplayShowHomeEnabled(false);  
        //添加Tabs  
        mTabs=new ArrayList<ActionBar.Tab>();  
          
        ActionBar.Tab tab0=mActionBar.newTab();  
        tab0.setText(getString(R.string.title_section1));  
        tab0.setTabListener(this);  
        mTabs.add(tab0);  
        mActionBar.addTab(tab0);  
          
        ActionBar.Tab tab1=mActionBar.newTab();  
        tab1.setText(getString(R.string.title_section2));  
        tab1.setTabListener(this);  
        mTabs.add(tab1);  
        mActionBar.addTab(tab1);  
          
        ActionBar.Tab tab2=mActionBar.newTab();  
        tab2.setText(getString(R.string.title_section3));  
        tab2.setTabListener(this);  
        mTabs.add(tab2);  
        mActionBar.addTab(tab2);  
        final FragmentManager fragmentManager = getSupportFragmentManager();
        //获取ViewPager  
        mViewPager=(ViewPager)findViewById(R.id.tab_pager);  
        //初始化mViews  
//        mViews=new ArrayList<View>();  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_0, null));  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_1, null));  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_2, null));  
        //初始化mAdapter  
        mAdapter=new ViewPagerAdapter(fragmentManager);  
        mViewPager.setAdapter(mAdapter);  
        mViewPager.setOnPageChangeListener(this);  
        //默认显示第二项  
        mViewPager.setCurrentItem(0);  
          
        LoadDB();
    }  
      
    private void LoadDB(){
    	CategoryArchiveInfo.getInstance(this);
    	CategoryInfo.getInstance(this);
    	MeterialInfo.getInstance(this);
    	PersonInfo.getInstance(this);
    	SeasonInfo.getInstance(this);
    	StorageLocationInfo.getInstance(this);
    	StyleInfo.getInstance(this);
    	ThicknessInfo.getInstance(this);
    	WearPlaceInfo.getInstance(this);
    }
  
    @Override  
    public void onTabReselected(Tab mTab, FragmentTransaction arg1)   
    {  
          
    }  
  
    @Override  
    public void onTabSelected(Tab mTab, FragmentTransaction arg1)   
    {  
        if(mViewPager!=null)  
        {  
           mViewPager.setCurrentItem(mTab.getPosition());  
        }  
    }  
  
    @Override  
    public void onTabUnselected(Tab mTab, FragmentTransaction arg1)   
    {  
          
    }  
  
  
    @Override  
    public void onPageScrollStateChanged(int arg0)   
    {  
          
    }  
  
  
    @Override  
    public void onPageScrolled(int arg0, float arg1, int arg2)   
    {  
          
    }  
  
  
    @Override  
    public void onPageSelected(int Index)   
    {  
        //设置当前要显示的View  
        mViewPager.setCurrentItem(Index);  
        //选中对应的Tab  
        mActionBar.selectTab(mTabs.get(Index));  
    }  
  
}  
//
//public class MainActivity extends ActionBarActivity 
//implements ActionBar.TabListener,OnGestureListener {//OnNavigationListener
//
//	private ViewPager mTabPager;
//	GestureDetector detector;
//    /**
//     * The serialization (saved instance state) Bundle key representing the
//     * current dropdown position.
//     */
//    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
//    private static final String STATE_SELECTED_ITEM = "selected_item";
//    final int FLIP_DISTANCE = 50;
//    int m_tabPosition = 0;
//    
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Set up the action bar to show a dropdown list.
//        final ActionBar actionBar = getSupportActionBar();
////        actionBar.setDisplayShowTitleEnabled(false);
////        actionBar.setDisplayShowHomeEnabled(false);  
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//NAVIGATION_MODE_LIST
//
//        // Set up the dropdown list navigation in the action bar.
////        actionBar.setListNavigationCallbacks(
////                // Specify a SpinnerAdapter to populate the dropdown list.
////                new ArrayAdapter<String>(
////                        actionBar.getThemedContext(),
////                        android.R.layout.simple_list_item_1,
////                        android.R.id.text1,
////                        new String[] {
////                                getString(R.string.title_section1),
////                                getString(R.string.title_section2),
////                                getString(R.string.title_section3),
////                        }),
////                this);
//        actionBar.addTab(actionBar.newTab().setText(getString(R.string.title_section1)).setTabListener(this));
//        actionBar.addTab(actionBar.newTab().setText(getString(R.string.title_section2)).setTabListener(this));
//        actionBar.addTab(actionBar.newTab().setText(getString(R.string.title_section3)).setTabListener(this));
//        
//        detector = new GestureDetector(this,this);
////        CategoryArchiveInfo.getInstance(this);
////        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dat.db", null);  
////        SQLiteDatabase db = helper.getWritableDatabase();  
////        DaoMaster daoMaster = new DaoMaster(db);  
////        DaoSession daoSession = daoMaster.newSession();  
////        CategoryArchiveDao noteDao = daoSession.getCategoryArchiveDao();  
////        CategoryArchive note = new CategoryArchive(3, 2, 0, "2014/06/07 12:37:00","0");  
////        noteDao.insert(note);  
////        if(CommonDefine.isDebug){
////    		Log.d("CategoryArchive Inserted!");
////		}
//    }
//
//	private void createViewAndFragment(){
//	final FragmentManager fragmentManager = getFragmentManager();
//	final FragmentTransaction transaction = fragmentManager.beginTransaction();
//	
////    mTabPager = getView(R.id.tab_pager);
////    mTabPagerAdapter = new TabPagerAdapter(getFragmentManager(),this);
////    mTabPager.setAdapter(mTabPagerAdapter);
////    mTabPager.setOnPageChangeListener(mTabPagerListener);
//
//    final String POSITION_TAG = "tab-pager-position";
//    final String CATEGORY_TAG = "tab-pager-category";
//    final String HOMEPAGE_TAG = "tab-pager-homepage";
//
//
////	mHomepageFragment =  fragmentManager
////			.findFragmentByTag(HOMEPAGE_TAG);
//
////	if (mContextMenuFragment == null) {
////		Log.e("HJJ", "ContextMenuFragmentd == null");
////		mContextMenuFragment = new ContextMenuFragment();
////		mArrayFragment = new ArraListFragment();
////		mHomepageFragment = new HomepageFragment();
////
////		transaction.add(R.id.tab_pager, mContextMenuFragment, POSITION_TAG);
////		transaction.add(R.id.tab_pager, mArrayFragment, CATEGORY_TAG);
////		transaction.add(R.id.tab_pager, mHomepageFragment, HOMEPAGE_TAG);
////	}
//
//
////	transaction.hide(mHomepageFragment);
//	
//    transaction.commitAllowingStateLoss();
//    fragmentManager.executePendingTransactions();
//}
//    
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        // Restore the previously serialized current dropdown position.
//        if (savedInstanceState.containsKey(STATE_SELECTED_ITEM)) {//STATE_SELECTED_NAVIGATION_ITEM
//            getSupportActionBar().setSelectedNavigationItem(
//                    savedInstanceState.getInt(STATE_SELECTED_ITEM));//STATE_SELECTED_NAVIGATION_ITEM
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        // Serialize the current dropdown position.
//        outState.putInt(STATE_SELECTED_ITEM,
//                getSupportActionBar().getSelectedNavigationIndex());//STATE_SELECTED_NAVIGATION_ITEM
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
////    @Override
////    public boolean onNavigationItemSelected(int position, long id) {
////        // When the given dropdown item is selected, show its contents in the
////        // container view.
////        getSupportFragmentManager().beginTransaction()
////                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
////                .commit();
////        return true;
////    }
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){
//    	
//    }
//    
//    @Override
//    public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){
//    	m_tabPosition = tab.getPosition();
//    	if(m_tabPosition == TabState.HOMEPAGE.ordinal()){
//    	 
//    	}
//    	getSupportFragmentManager().beginTransaction()
//      .replace(R.id.container, PlaceholderFragment.newInstance(tab.getPosition() + 1))
//      .commit();
//    }
//    
//    @Override
//    public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){
//    	
//    }
//
//    public boolean onTouchEvent(MotionEvent event){
////    	float x = event.getX();
////    	float y = event.getY();
////    	Toast toast = Toast.makeText(this, "X: "+String.valueOf(x)+ "Y: "+String.valueOf(y), Toast.LENGTH_SHORT);
////    	toast.show();
//    	return detector.onTouchEvent(event);
////    	return true;
//    }
//    
//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);//.fragment_main
//            Button buttonView = (Button) rootView.findViewById(R.id.button1);
//            TextView textView = (TextView) rootView.findViewById(R.id.textView1);
//            buttonView.setOnClickListener(new OnClickListener(){
//            	@Override
//            	public void onClick(View source){
//            		Intent intent = new Intent();
////            		ComponentName comp = new ComponentName(MainActivity.this,testActivity.class);
//            		intent.setAction("testaction");
//            		startActivity(intent);
//            	}
//            });
////            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }
//
//	@Override
//	public boolean onDown(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		// TODO Auto-generated method stub
//		if( e1.getX() - e2.getX() > FLIP_DISTANCE){//从右到左
//			m_tabPosition = m_tabPosition + 1;
//			if(m_tabPosition == 3){
//				m_tabPosition = 2;
//			}
//			getSupportFragmentManager().beginTransaction()
//		      .replace(R.id.container, PlaceholderFragment.newInstance(m_tabPosition + 1))
//		      .commit();
//			return true;
//		}
//		else if( e2.getX() - e1.getX() > FLIP_DISTANCE){//从左到右
//			m_tabPosition = m_tabPosition - 1;
//			if(m_tabPosition < 0){
//				m_tabPosition = 0;
//			}
//			getSupportFragmentManager().beginTransaction()
//		      .replace(R.id.container, PlaceholderFragment.newInstance(m_tabPosition + 1))
//		      .commit();
//			return true;
//		}
//		return false;
//	}
//
//}

//
//import com.activity.adapter.TabPagerAdapter;
//import com.activity.fragments.ArraListFragment;
//import com.activity.fragments.ContextMenuFragment;
//import com.activity.fragments.HomepageFragment;
//import com.activity.listener.MyTabListener;
//import com.activity.listener.TabPagerListener;
//import com.common.clothe.CommonDefine.TabState;
//import com.example.clotheme.R;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.provider.ContactsContract.Contacts;
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.app.ListFragment;
//import android.app.ActionBar.Tab;
//import android.app.LoaderManager.LoaderCallbacks;
//import android.content.CursorLoader;
//import android.content.Loader;
//import android.database.Cursor;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ContextMenu.ContextMenuInfo;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.SimpleCursorAdapter;
//import android.widget.Toast;
//import android.widget.SearchView.OnQueryTextListener;
//
//public class MainActivity extends Activity {
//	private ActionBar mActionBar;
//	private static final TabState DEFAULT_TAB = TabState.HOMEPAGE;
//    private TabState mCurrentTab = DEFAULT_TAB;
//    private final MyTabListener mTabListener = new MyTabListener(this);
////    private ArraListFragment mArrayFragment;
//    private HomepageFragment mHomepageFragment;
////    private ContextMenuFragment mContextMenuFragment;
//    private ViewPager mTabPager;
//    private TabPagerAdapter mTabPagerAdapter;	
//    private final TabPagerListener mTabPagerListener = new TabPagerListener(this);
//    
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tab_view_pager);
//		initActionBar();
//		createViewAndFragment();
//	}
//	
//	private void createViewAndFragment(){
//		final FragmentManager fragmentManager = getFragmentManager();
//		final FragmentTransaction transaction = fragmentManager.beginTransaction();
//		
//        mTabPager = getView(R.id.tab_pager);
//        mTabPagerAdapter = new TabPagerAdapter(getFragmentManager(),this);
//        mTabPager.setAdapter(mTabPagerAdapter);
//        mTabPager.setOnPageChangeListener(mTabPagerListener);
//
//        final String POSITION_TAG = "tab-pager-position";
//        final String CATEGORY_TAG = "tab-pager-category";
//        final String HOMEPAGE_TAG = "tab-pager-homepage";
//
//        // Create the fragments and add as children of the view pager.
//        // The pager adapter will only change the visibility; it'll never create/destroy
//        // fragments.
//        // However, if it's after screen rotation, the fragments have been re-created by
//        // the fragment manager, so first see if there're already the target fragments
//        // existing.
////		mContextMenuFragment = (ContextMenuFragment) fragmentManager
////				.findFragmentByTag(POSITION_TAG);
////		mArrayFragment = (ArraListFragment) fragmentManager
////				.findFragmentByTag(CATEGORY_TAG);
//		mHomepageFragment =  fragmentManager
//				.findFragmentByTag(HOMEPAGE_TAG);
//
////		if (mContextMenuFragment == null) {
////			Log.e("HJJ", "ContextMenuFragmentd == null");
////			mContextMenuFragment = new ContextMenuFragment();
////			mArrayFragment = new ArraListFragment();
////			mHomepageFragment = new HomepageFragment();
////
////			transaction.add(R.id.tab_pager, mContextMenuFragment, POSITION_TAG);
////			transaction.add(R.id.tab_pager, mArrayFragment, CATEGORY_TAG);
////			transaction.add(R.id.tab_pager, mHomepageFragment, HOMEPAGE_TAG);
////		}
//
//		// Hide all fragments for now. We adjust visibility when we get
//		// onSelectedTabChanged()
//		// from ActionBarAdapter.
////		transaction.hide(mContextMenuFragment);
////		transaction.hide(mArrayFragment);
//		transaction.hide(mHomepageFragment);
//		
//        transaction.commitAllowingStateLoss();
//        fragmentManager.executePendingTransactions();
//	}
//	
//    
//    
//	private void initActionBar(){
//    	mActionBar = getActionBar();
//    	mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//    	// ȥ��Ĭ�ϱ�����
////    	mActionBar.setDisplayShowHomeEnabled(false);  
////    	mActionBar.setDisplayShowTitleEnabled(false); 
//        // Set up tabs
//        addTab(TabState.HOMEPAGE, R.drawable.ic_tab_homepage,getString(R.string.title_section1));
//        addTab(TabState.CATEGORY, R.drawable.ic_tab_category,getString(R.string.title_section2));
//        addTab(TabState.POSITION, R.drawable.ic_tab_starred,getString(R.string.title_section3));
//	}
//	
//    
//    
//    @SuppressWarnings("unchecked")
//    public <T extends View> T getView(int id) {
//        T result = (T)findViewById(id);
//        if (result == null) {
//            throw new IllegalArgumentException("view 0x" + Integer.toHexString(id)
//                    + " doesn't exist");
//        }
//        return result;
//    }
//    
//    protected static void showFragment(FragmentTransaction ft, Fragment f) {
//        if ((f != null) && f.isHidden()) ft.show(f);
//    }
//
//    protected static void hideFragment(FragmentTransaction ft, Fragment f) {
//        if ((f != null) && !f.isHidden()) ft.hide(f);
//    }
//    
//	private void addTab(TabState tabState, int icon, String desc) {
//		final Tab tab = mActionBar.newTab();
//		tab.setTag(tabState);
//		tab.setTabListener(mTabListener);
//		tab.setIcon(icon);
//		tab.setText(desc);
//		mActionBar.addTab(tab);
//	}
//
//	public Fragment getFragment(int position) {
////		if (position == TabState.POSITION.ordinal()) {
////			return mContextMenuFragment;
////		} else if (position == TabState.CATEGORY.ordinal()) {
////			return mArrayFragment;
////		} else 
//			if (position == TabState.HOMEPAGE.ordinal()) {
//			return mHomepageFragment;
//		}
//        throw new IllegalArgumentException("position: " + position);
//    }
//	
//	/**
//     * Change the current tab, and notify the listener.
//     */
//    public void setCurrentTab(TabState tab) {
//        setCurrentTab(tab, true);
//    }
//
//    /**
//     * Change the current tab
//     */
//    public void setCurrentTab(TabState tab, boolean notifyListener) {
//        if (tab == null) throw new NullPointerException();
//        //ʵ���ϰ���Contacts�е���ƣ������Ӧ����Ч�ģ����ڴ�ģ������У����������⣬�����ʱ���˶�ȥ��
////        if (tab == mCurrentTab) {
////            return;
////        }
//        mCurrentTab = tab;
//
//        int index = mCurrentTab.ordinal();
//        if ((mActionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS)
//                && (index != mActionBar.getSelectedNavigationIndex())) {
//            mActionBar.setSelectedNavigationItem(index);
//        }
//
//        if (notifyListener) onSelectedTabChanged(tab);
//    }
//    
//    private void onSelectedTabChanged(TabState tab){
//    	FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        int tabIndex = tab.ordinal();
//        Log.e("HJJ", "tabIndex:" + tabIndex);
//        switch (tab) {
////        case POSITION:
////        	if(mContextMenuFragment!=null){
////            	mTabPager.setCurrentItem(tabIndex, false);
////        	}
////        	showFragment(ft, mContextMenuFragment);
////            hideFragment(ft, mArrayFragment);
////            hideFragment(ft, mHomepageFragment);
////            break;
////        case CATEGORY:
////        	if(mArrayFragment!=null){
////        		mTabPager.setCurrentItem(tabIndex, false);
////        	}
////        	hideFragment(ft, mContextMenuFragment);
////            hideFragment(ft, mHomepageFragment);
////            showFragment(ft, mArrayFragment);
////            break;
//        case HOMEPAGE:
//        	if(mHomepageFragment!=null){
//        		mTabPager.setCurrentItem(tabIndex, false);
//        	}
////        	hideFragment(ft, mContextMenuFragment);
////            hideFragment(ft, mArrayFragment);
//            showFragment(ft, mHomepageFragment);
//            break;
//        }
//    	if (!ft.isEmpty()) {
//    		Log.e("HJJ", "not ft isEmpty");
//            ft.commitAllowingStateLoss();
//            fragmentManager.executePendingTransactions();
//            // When switching tabs, we need to invalidate options menu, but executing a
//            // fragment transaction does it implicitly.  We don't have to call invalidateOptionsMenu
//            // manually.
//        }
//    }
//}



