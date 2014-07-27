package com.example.photowallfallsdemo;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.activity.adapter.CategoryDetailWaterFallAdapter;
import com.activity.adapter.ViewPagerAdapter;
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

public class CategoryDetailWaterFallActivity extends FragmentActivity implements TabListener,OnPageChangeListener{

    private ActionBar mActionBar;  
    private ViewPager mViewPager;  
    private CategoryDetailWaterFallAdapter mAdapter;  
    private ArrayList<View> mViews;  
    private ArrayList<ActionBar.Tab> mTabs;  
    
    public static String m_CategoryName = null;
    public static int m_seasonID = 4;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.tab_view_pager);  
        
        Intent intent = getIntent();
        m_CategoryName = intent.getStringExtra("categoryname");
        
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
        tab0.setText(getString(R.string.title_section_all));  
        tab0.setTabListener(this);  
        mTabs.add(tab0);  
        mActionBar.addTab(tab0);  
          
        ActionBar.Tab tab1=mActionBar.newTab();  
        tab1.setText(getString(R.string.title_section_spring));  
        tab1.setTabListener(this);  
        mTabs.add(tab1);  
        mActionBar.addTab(tab1);  
          
        ActionBar.Tab tab2=mActionBar.newTab();  
        tab2.setText(getString(R.string.title_section_summer));  
        tab2.setTabListener(this);  
        mTabs.add(tab2);  
        mActionBar.addTab(tab2);  
        
        ActionBar.Tab tab3=mActionBar.newTab();  
        tab3.setText(getString(R.string.title_section_autumn));  
        tab3.setTabListener(this);  
        mTabs.add(tab3);  
        mActionBar.addTab(tab3);  
        
        ActionBar.Tab tab4=mActionBar.newTab();  
        tab4.setText(getString(R.string.title_section_winter));  
        tab4.setTabListener(this);  
        mTabs.add(tab4);  
        mActionBar.addTab(tab4);  
        
        final FragmentManager fragmentManager = getSupportFragmentManager();
        //获取ViewPager  
        mViewPager=(ViewPager)findViewById(R.id.tab_pager);  
        //初始化mViews  
//        mViews=new ArrayList<View>();  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_0, null));  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_1, null));  
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.layout_2, null));  
        //初始化mAdapter  
        mAdapter=new CategoryDetailWaterFallAdapter(fragmentManager);  
        mViewPager.setAdapter(mAdapter);  
        mViewPager.setOnPageChangeListener(this);  
        //默认显示第二项  
        mViewPager.setCurrentItem(0);  
          
//        LoadDB();
    }  
      
//    private void LoadDB(){
//    	CategoryArchiveInfo.getInstance(this);
//    	CategoryInfo.getInstance(this);
//    	MeterialInfo.getInstance(this);
//    	PersonInfo.getInstance(this);
//    	SeasonInfo.getInstance(this);
//    	StorageLocationInfo.getInstance(this);
//    	StyleInfo.getInstance(this);
//    	ThicknessInfo.getInstance(this);
//    	WearPlaceInfo.getInstance(this);
//    }
  
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
           switch(mTab.getPosition()){
           case 0:
           	m_seasonID = 4;
           	break;
           case 1:
           	m_seasonID = 0;
           	break;
           case 2:
           	m_seasonID = 1;
           	break;
           case 3:
           	m_seasonID = 2;
           	break;
           case 4:
           	m_seasonID = 3;
           	break;
           }
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
//        switch(Index){
//        case 0:
//        	m_seasonID = 4;
//        	break;
//        case 1:
//        	m_seasonID = 0;
//        	break;
//        case 2:
//        	m_seasonID = 1;
//        	break;
//        case 3:
//        	m_seasonID = 2;
//        	break;
//        case 4:
//        	m_seasonID = 3;
//        	break;
//        }
    }  
  
}  