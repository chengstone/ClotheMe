package com.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.activity.fragments.AllSeasonFragments;
import com.activity.fragments.AutumnFragments;
import com.activity.fragments.BlankPageFragments;
import com.activity.fragments.CategoryListPageFragment;
import com.activity.fragments.HomePageFragment;
import com.activity.fragments.SpringFragments;
import com.activity.fragments.SummerFragments;
import com.activity.fragments.WinterFragments;
import com.example.photowallfallsdemo.CategoryDetailWaterFallActivity;


public class CategoryDetailWaterFallAdapter extends FragmentPagerAdapter {  
  
    //定义五个Fragment的索引  
    public static final int Fragment_Index_0=0;  
    public static final int Fragment_Index_1=1;  
    public static final int Fragment_Index_2=2;  
    public static final int Fragment_Index_3=3;  
    public static final int Fragment_Index_4=4;  
      
    public Fragment m_Fragment0 = null;
    public Fragment m_Fragment1 = null;
    public Fragment m_Fragment2 = null;
    public Fragment m_Fragment3 = null;
    public Fragment m_Fragment4 = null;
    public CategoryDetailWaterFallAdapter(FragmentManager fragmentManager)   
    {  
        super(fragmentManager);  
    }  
  
    @Override  
    public Fragment getItem(int Index)   
    {  
        Fragment mFragemnt=null;  
        switch(Index)  
        {  
          case Fragment_Index_0:  
        	  if(m_Fragment0 == null){
        		  m_Fragment0 = new AllSeasonFragments();  
        	  }
              mFragemnt=m_Fragment0;  
//              CategoryDetailWaterFallActivity.m_seasonID = 4;
              break;  
          case Fragment_Index_1:  
        	  if(m_Fragment1 == null){
        		  m_Fragment1 = new SpringFragments();  
        	  }
              mFragemnt=m_Fragment1;  
//              CategoryDetailWaterFallActivity.m_seasonID = 0;
              break;  
          case Fragment_Index_2:  
        	  if(m_Fragment2 == null){
        		  m_Fragment2 = new SummerFragments();  
        	  }
              mFragemnt=m_Fragment2;  
//              CategoryDetailWaterFallActivity.m_seasonID = 1;
              break;  
          case Fragment_Index_3:  
        	  if(m_Fragment3 == null){
        		  m_Fragment3 = new AutumnFragments();  
        	  }
              mFragemnt=m_Fragment3;  
//              CategoryDetailWaterFallActivity.m_seasonID = 2;
              break;  
          case Fragment_Index_4:  
        	  if(m_Fragment4 == null){
        		  m_Fragment4 = new WinterFragments();  
        	  }
              mFragemnt=m_Fragment4;  
//              CategoryDetailWaterFallActivity.m_seasonID = 3;
              break;  
          default:
        	  mFragemnt=new BlankPageFragments();	//AllSeasonFragments();  
//            CategoryDetailWaterFallActivity.m_seasonID = 4;
            break;    
        }  
        return mFragemnt;  
    }  
  
    @Override  
    public int getCount()   
    {  
        return 5;  
    }  
  
}  