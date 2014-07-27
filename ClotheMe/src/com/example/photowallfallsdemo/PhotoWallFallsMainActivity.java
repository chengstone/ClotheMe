package com.example.photowallfallsdemo;

import com.example.clotheme.R;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class PhotoWallFallsMainActivity extends Activity {

	public static String m_CategoryName = null;
	public static PhotoWallFallsMainActivity ms_instance = null;
	public static PhotoWallFallsMainActivity getInstance(){
		if(ms_instance == null){
			//Toast.makeText(PhotoWallFallsMainActivity.class, "PhotoWallFallsMainActivity ms_instance is null", Toast.LENGTH_SHORT);
			return null;
		}
		return ms_instance;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photowallfalls_activity_main);
		
		Intent intent = getIntent();
        m_CategoryName = intent.getStringExtra("categoryname");
        ms_instance = this;
        
	}

}
