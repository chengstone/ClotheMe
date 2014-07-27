package com.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clotheme.R;


public class SummerFragments extends Fragment {

	public static String m_CategoryName = null;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context cxt = this.getActivity();
		View mView = inflater.inflate(R.layout.photowallfalls_activity_summer, container,
				false);

		return mView;
	}

}