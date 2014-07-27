package com.huewu.pla.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activity.adapter.MyStaggeredAdapter;
import com.activity.task.MyContentProvider;
import com.activity.task.MyContentTask;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.example.clotheme.R;

public class MyPullToRefreshSampleActivity extends FragmentActivity implements IXListViewListener {
    public ImageFetcher mImageFetcher;
    public XListView mAdapterView = null;
    public MyStaggeredAdapter mAdapter = null;
    private int currentPage = 0;
//    MyContentTask task = new MyContentTask(this, 2,this);
    MyContentProvider task = new MyContentProvider(this, 2, this);
    private String m_CategoryName = null;
//    static MyPullToRefreshSampleActivity ms_instance = null;
//    
//    static public MyPullToRefreshSampleActivity getInstance(){
//    	return ms_instance;
//    }
//    private MyPullToRefreshSampleActivity(){
//    	
//    }

    /**
     * 添加内容
     * 
     * @param pageindex
     * @param type
     *            1为下拉刷新 2为加载更多
     */
    private void AddItemToContainer(int pageindex, int type) {
//        if (task.getStatus() != Status.RUNNING) {
//            String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
//            Log.d("MainActivity", "current url:" + url);
//            MyContentTask task = new MyContentTask(this, type,this);
//            task.execute(url);
//
//        }
    	task.DoLoadPicture(m_CategoryName,type);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pull_to_refresh_sample);
        mAdapterView = (XListView) findViewById(R.id.list);
        mAdapterView.setPullLoadEnable(false);
        mAdapterView.setXListViewListener(this);

        mAdapter = new MyStaggeredAdapter(this, mAdapterView,this);

        mImageFetcher = new ImageFetcher(this, 240);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        
        Intent intent = getIntent();
        m_CategoryName = intent.getStringExtra("categoryname");
        Toast.makeText(this, m_CategoryName, Toast.LENGTH_SHORT);
//        ms_instance = this;
//        task.setAdapter(mAdapter);
//        task.setAdapterView(mAdapterView);
//        mAdapter.setImageFetcher(mImageFetcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT);
        mImageFetcher.setExitTasksEarly(false);
        mAdapterView.setAdapter(mAdapter);
        AddItemToContainer(currentPage, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        AddItemToContainer(++currentPage, 1);

    }

    @Override
    public void onLoadMore() {
        AddItemToContainer(++currentPage, 2);

    }
}// end of class
