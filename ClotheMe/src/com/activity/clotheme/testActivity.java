package com.activity.clotheme;

import com.example.clotheme.R;

import android.app.Activity;
import android.os.Bundle;

public class testActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_view_pager);
	}
}
