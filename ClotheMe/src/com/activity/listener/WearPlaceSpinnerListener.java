package com.activity.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class WearPlaceSpinnerListener implements OnItemSelectedListener{

//  @Override 
  public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3){ 
      // 在这里我们复习一下Toast的用法  
      Toast.makeText(parent.getContext(), 
                                "The planet is "+  parent.getItemAtPosition(pos).toString(), 
                                 Toast.LENGTH_LONG).show(); 
  }
//  @Override 
  public void onNothingSelected(AdapterView<?> arg0) {  
      //nothing to do 
  } 

}