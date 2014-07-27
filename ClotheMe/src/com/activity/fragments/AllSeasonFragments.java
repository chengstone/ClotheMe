package com.activity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.activity.clotheme.ImagePreviewActivity;
import com.common.clothe.CommonDefine;
import com.example.clotheme.R;
import com.example.photowallfallsdemo.PhotoWallFallsMainActivity;
import com.functionCtrl.clotheme.PictureOperatingCtrl;
import com.spring.sky.image.upload.SelectPicActivity;

public class AllSeasonFragments extends Fragment {
//	private Bitmap photo = null;
//	private String fileName;
//	private static String picturepath = CommonDefine.CommonString.PICTUREPATH;
//
//	private int pickPictureflag;	//1-select picture 0-take picture
//	Button btn;
//	public final static String PAIZHAO = "android.intent.action.PAIZHAO";
//	public final static String TAKE_PIC = "android.media.action.IMAGE_CAPTURE";
//	public final static String CATEGORY_TAKE = "com.clotheme.intent.category.TAKE_PIC";

	/**
	 * 选择文件
	 */
//	public static final int TO_SELECT_PHOTO = 3;
	public static String m_CategoryName = null;
//	public static PhotoWallFallsMainActivity ms_instance = null;
//	public static PhotoWallFallsMainActivity getInstance(){
//		if(ms_instance == null){
//			//Toast.makeText(PhotoWallFallsMainActivity.class, "PhotoWallFallsMainActivity ms_instance is null", Toast.LENGTH_SHORT);
//			return null;
//		}
//		return ms_instance;
//	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.photowallfalls_activity_main);
//		
//		Intent intent = getIntent();
//        m_CategoryName = intent.getStringExtra("categoryname");
//        ms_instance = this;
//	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context cxt = this.getActivity();
		View mView = inflater.inflate(R.layout.photowallfalls_activity_main, container,
				false);
//		if (mView != null) {
//			btn = (Button) mView.findViewById(R.id.button1);
//			btn.setText(R.string.paizhao);
//			btn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
//					if (state.equals(Environment.MEDIA_MOUNTED)) {
//						// Intent intent = new Intent();
//						// intent.setAction(PAIZHAO);//TAKE_PIC
//						// intent.addCategory(CATEGORY_TAKE);
//						// startActivity(intent);
//						// startActivityForResult(intent, 1888);//0
//						Intent intent = new Intent(HomePageFragment.this
//								.getActivity(), SelectPicActivity.class);
//						startActivityForResult(intent, TO_SELECT_PHOTO);
//					}
//
//				}
//			});
//		}
		return mView;
	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
//			String picPath = data
//					.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
//			pickPictureflag = data.getIntExtra(SelectPicActivity.KEY_SELECT_PICTURE_FLAG, 0);
//			Toast.makeText(HomePageFragment.this.getActivity(),
//					"最终选择的图片=" + picPath, Toast.LENGTH_LONG).show();
////			com.common.clothe.Log.i("最终选择的图片=" + picPath);
////			 try {  
////				 photo = BitmapUtil.getBitmapByPath(picPath, BitmapUtil.getOptions(picPath), 200, 200);  
////				 photo = ImageCacheUtil.getResizedBitmap(null, null,   
////						 HomePageFragment.this.getActivity(), data.getData(), target, false);  
//				 PictureOperatingCtrl poc = new PictureOperatingCtrl();
//				 photo = poc.getBitmapSafely(picPath);
//				 fileName = picPath;
////			 } catch (FileNotFoundException e) {  
////				  e.printStackTrace();  
////				 }  
////			photo = BitmapFactory.decodeFile(picPath);
////			imageView.setImageBitmap(photo);
//			DoActivityResult(requestCode, resultCode, data);
////			poc = null;
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//
//	public void DoActivityResult(int requestCode, int resultCode, Intent data) {
//	 try {
////	 if (requestCode != 1888) {
////	 return;
////	 }
////	 super.onActivityResult(requestCode, resultCode, data);
//	 // 两种方式获得拍摄照片的返回值
////	 Uri uri = data.getData();
////	 if (uri != null) {
////	 photo = BitmapFactory.decodeFile(uri.getPath());
////	 }
////	 if (photo == null) {
////	 Bundle bundle = data.getExtras();
////	 if (bundle != null) {
////	 photo = (Bitmap) bundle.get("data");
//	 if(photo!=null){
//		 PictureOperatingCtrl poc = new PictureOperatingCtrl();
//		 if(pickPictureflag == 0){
//			 fileName = poc.SavePicInLocal(photo);// 保存到本地
//				Toast.makeText(HomePageFragment.this.getActivity(), fileName,
//				Toast.LENGTH_LONG).show();	 
//		 }
//		 
//	 // 生成一个Intent对象
//		 Intent intent = new Intent();
//		 intent.putExtra("picpath", fileName);
//		 intent.setClass(HomePageFragment.this.getActivity(),ImagePreviewActivity.class);
//	  // 启动intent的Activity
//		 HomePageFragment.this.getActivity().startActivity(intent);
//	 } else {
//		 Toast.makeText(HomePageFragment.this.getActivity(), "未拍摄照片",
//				 Toast.LENGTH_LONG).show();
//	 // 生成一个Intent对象
//	 // Intent intent = new Intent();
//	 // intent.setClass(TackPhoto.this, JcjlEditActivity.class);
//	 // TackPhoto.this.startActivity(intent);
//	 }
////	 }
//	
//	 } catch (Exception e) {
//	 Toast.makeText(HomePageFragment.this.getActivity(),
//	 "onActivityResult Exception:"+e.getMessage(), Toast.LENGTH_LONG)
//	 .show();
//	 }
//	 }
//
//
//	
//
//	private String getPictureFilepath() {
//		return String.format(picturepath, HomePageFragment.this.getActivity()
//				.getApplicationInfo().packageName);
//	}
}