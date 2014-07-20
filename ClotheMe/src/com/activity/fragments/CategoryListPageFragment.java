package com.activity.fragments;

import java.util.ArrayList;

import com.activity.clotheme.ImagePreviewActivity;
import com.common.clothe.CommonDefine;
import com.example.clotheme.R;
import com.functionCtrl.clotheme.CommonFunctionCtrl;
import com.functionCtrl.clotheme.PictureOperatingCtrl;
import com.logicalModelLayer.Implements.CategoryInfo;
import com.spring.sky.image.upload.SelectPicActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryListPageFragment extends ListFragment{
private static final String TAG = "ListFragmentImpl";
    
//	private CategoryListViewAdapter adapter = null;  
    private ListView selfList;
    
    private String[] categorys = null;
    private ArrayList<String> categoryNameItems = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.category_list_fragment, container, false);
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        
//        adapter = new CategoryListViewAdapter (getActivity());  
//        setListAdapter(adapter);  
        
        categoryNameItems = CategoryInfo.getInstance(this.getActivity()).getAllCategory();
		categoryNameItems.add("添加分类");
        categorys = CommonFunctionCtrl.trans(categoryNameItems);
        
        // 设置ListFragment默认的ListView，即@id/android:list
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), 
                android.R.layout.simple_list_item_1, categorys));
        
    }
   
    public void onListItemClick(ListView parent, View v, 
            int position, long id) {
        Log.d(TAG, "onListItemClick");
        Toast.makeText(getActivity(), 
                "You have selected " + categorys[position],
                Toast.LENGTH_SHORT).show();
    }    
}
