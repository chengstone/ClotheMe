package com.activity.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.activity.clotheme.ImagePreviewActivity;
import com.common.clothe.CommonDefine;
import com.daogen.clotheme.Category;
import com.daogen.clotheme.Meterial;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.huewu.pla.sample.MyPullToRefreshSampleActivity;
import com.logicalModelLayer.Implements.CategoryInfo;
import com.logicalModelLayer.Implements.MeterialInfo;

public class MyContentProvider {

    private Context mContext;
    private int mType = 1;
    
    private MyPullToRefreshSampleActivity act = null;

    public MyContentProvider(Context context, int type, MyPullToRefreshSampleActivity in_act) {
        super();
        mContext = context;
        mType = type;
        act = in_act;//MyPullToRefreshSampleActivity.getInstance();
    }

//    @Override
//    protected List<DuitangInfo> doInBackground(String... params) {
//        try {
//            return parseNewsJSON(params[0]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    @Override
    protected void PostDataSet(List<DuitangInfo> result) {
        if (mType == 1) {

            act.mAdapter.addItemTop(result);
            act.mAdapter.notifyDataSetChanged();
            act.mAdapterView.stopRefresh();

        } else if (mType == 2) {
        	act.mAdapterView.stopLoadMore();
        	act.mAdapter.addItemLast(result);
        	act.mAdapter.notifyDataSetChanged();
        }

    }

//    @Override
//    protected void onPreExecute() {
//    }

    public int DoLoadPicture(String in_CategoryName,int in_type) {
    	mType = in_type;
        List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
        Category category = CategoryInfo.getInstance(mContext).getCategory(in_CategoryName);
        MeterialInfo meterialInfo = MeterialInfo.getInstance(
        		mContext);
        long val = category.getId();
        List<Meterial> result = meterialInfo.getMeterialFromCategoryID(val);
        if(result == null){
        	return CommonDefine.SYSTEM_ERROR;
        }
//        String json = "";
//        if (Helper.checkConnection(mContext)) {
//            try {
//                json = Helper.getStringFromUrl(url);
//
//            } catch (IOException e) {
//                Log.e("IOException is : ", e.toString());
//                e.printStackTrace();
//                return duitangs;
//            }
//        }
//        Log.d("MainActiivty", "json:" + json);

//        try {
//            if (null != json) {
//                JSONObject newsObject = new JSONObject(json);
//                JSONObject jsonObject = newsObject.getJSONObject("data");
//                JSONArray blogsJson = jsonObject.getJSONArray("blogs");
        BitmapFactory.Options options = new BitmapFactory.Options();
		 options.inJustDecodeBounds = true; 
		 Bitmap bitmap;
        
                for (int i = 0; i < result.size(); i++) {
//                    JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                    DuitangInfo newsInfo1 = new DuitangInfo();
                    newsInfo1.setAlbid(String.valueOf(result.get(i).getId()));
                    newsInfo1.setIsrc(result.get(i).getPicPath());
                    newsInfo1.setMsg(result.get(i).getDescription());
                    BitmapFactory.decodeFile(result.get(i).getPicPath(),options);
                    newsInfo1.setHeight(options.outHeight);
                    duitangs.add(newsInfo1);
                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        PostDataSet(duitangs);
        return CommonDefine.NO_ERROR;
    }
}
