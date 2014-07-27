package com.activity.adapter;

import java.util.LinkedList;
import java.util.List;

import me.maxwin.view.XListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.activity.fragments.HomePageFragment;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.clotheme.R;
//import com.huewu.pla.sample.MyPullToRefreshSampleActivity.MyStaggeredAdapter.MyViewHolder;
import com.huewu.pla.sample.MyPullToRefreshSampleActivity;



public class MyStaggeredAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<DuitangInfo> mInfos;
    private XListView mListView;
    
    private MyPullToRefreshSampleActivity act = null;

    public MyStaggeredAdapter(Context context, XListView xListView, MyPullToRefreshSampleActivity in_act) {
        mContext = context;
        mInfos = new LinkedList<DuitangInfo>();
        mListView = xListView;
        
        act = in_act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	MyViewHolder holder;
        DuitangInfo duitangInfo = mInfos.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
            convertView = layoutInflator.inflate(R.layout.infos_list, null);
            holder = new MyViewHolder();
            holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);
            holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
            convertView.setTag(holder);
        }

        holder = (MyViewHolder) convertView.getTag();
        holder.imageView.setImageWidth(duitangInfo.getWidth());
        holder.imageView.setImageHeight(duitangInfo.getHeight());
        holder.contentView.setText(duitangInfo.getMsg());
        act.mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView);
//        Toast.makeText(mContext, duitangInfo.getIsrc(),
//				Toast.LENGTH_SHORT).show();	 
        return convertView;
    }

    class MyViewHolder {
        ScaleImageView imageView;
        TextView contentView;
        TextView timeView;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mInfos.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public void addItemLast(List<DuitangInfo> datas) {
        mInfos.addAll(datas);
    }

    public void addItemTop(List<DuitangInfo> datas) {
        for (DuitangInfo info : datas) {
            mInfos.addFirst(info);
        }
    }
}