package com.activity.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.huewu.pla.sample.MyPullToRefreshSampleActivity;


public class MyContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

    private Context mContext;
    private int mType = 1;
    
    private MyPullToRefreshSampleActivity act = null;

    public MyContentTask(Context context, int type, MyPullToRefreshSampleActivity in_act) {
        super();
        mContext = context;
        mType = type;
        act = in_act;//MyPullToRefreshSampleActivity.getInstance();
    }

    @Override
    protected List<DuitangInfo> doInBackground(String... params) {
        try {
            return parseNewsJSON(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<DuitangInfo> result) {
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

    @Override
    protected void onPreExecute() {
    }

    public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
        List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
        String json = "";
        if (Helper.checkConnection(mContext)) {
            try {
                json = Helper.getStringFromUrl(url);

            } catch (IOException e) {
                Log.e("IOException is : ", e.toString());
                e.printStackTrace();
                return duitangs;
            }
        }
        Log.d("MainActiivty", "json:" + json);

        try {
            if (null != json) {
                JSONObject newsObject = new JSONObject(json);
                JSONObject jsonObject = newsObject.getJSONObject("data");
                JSONArray blogsJson = jsonObject.getJSONArray("blogs");

                for (int i = 0; i < blogsJson.length(); i++) {
                    JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                    DuitangInfo newsInfo1 = new DuitangInfo();
                    newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "" : newsInfoLeftObject.getString("albid"));
                    newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc"));
                    newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg"));
                    newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
                    duitangs.add(newsInfo1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return duitangs;
    }
}