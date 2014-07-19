package com.activity.clotheme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activity.fragments.HomePageFragment;
import com.activity.listener.PutPositionSpinnerListener;
import com.activity.listener.RemindFrequencySpinnerListener;
import com.activity.listener.SelectCategorySpinnerListener;
import com.activity.listener.SelectSeasonSpinnerListener;
import com.activity.listener.SelectStyleSpinnerListener;
import com.activity.listener.SelectThicknessSpinnerListener;
import com.activity.listener.WearPlaceSpinnerListener;
import com.activity.listener.WhoseClotheSpinnerListener;
import com.example.clotheme.R;
import com.functionCtrl.clotheme.BitmapUtil;
import com.functionCtrl.clotheme.CommonFunctionCtrl;
import com.functionCtrl.clotheme.PictureOperatingCtrl;
import com.logicalModelLayer.Implements.CategoryArchiveInfo;
import com.logicalModelLayer.Implements.CategoryInfo;
import com.logicalModelLayer.Implements.PersonInfo;
import com.logicalModelLayer.Implements.SeasonInfo;
import com.logicalModelLayer.Implements.StorageLocationInfo;
import com.logicalModelLayer.Implements.StyleInfo;
import com.logicalModelLayer.Implements.ThicknessInfo;
import com.logicalModelLayer.Implements.WearPlaceInfo;
import com.spring.sky.image.upload.SelectPicActivity;

public class ImagePreviewActivity extends Activity{
	private ImageView m_ImageView = null;
	private FinalBitmap fb;
	
	private Spinner m_WhoseClotheSpinner = null;
	private Spinner m_SelectCategorySpinner = null;
	private Spinner m_PutPositionSpinner = null;
	private Spinner m_SelectSeasonSpinner = null;
	private Spinner m_SelectThicknessSpinner = null;
	private Spinner m_SelectStyleSpinner = null;
	private Spinner m_RemindFrequencySpinner = null;
	private Spinner m_WearPlaceSpinner = null;
	
	private ArrayAdapter<String> m_WhoseClotheAdapter = null;
	private ArrayAdapter<String> m_SelectCategoryAdapter = null;
	private ArrayAdapter<String> m_PutPositionAdapter = null;
	private ArrayAdapter<String> m_SelectSeasonAdapter = null;
	private ArrayAdapter<String> m_SelectThicknessAdapter = null;
	private ArrayAdapter<String> m_SelectStyleAdapter = null;
	private ArrayAdapter<String> m_RemindFrequencyAdapter = null;
	private ArrayAdapter<String> m_WearPlaceAdapter = null;
	
	private ArrayList<String> personNameItems = null;
	private ArrayList<String> categoryNameItems = null;
	private ArrayList<String> locationItems = null;
	private ArrayList<String> seasonItems = null;
	private ArrayList<String> thicknessItems = null;
	private ArrayList<String> styleItems = null;
	private ArrayList<String> remindFrequencyItems = null;
	private ArrayList<String> wearPlaceItems = null;
	
	private Button ok_btn = null;
	private Button cancel_btn = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagepreview);
		fb = FinalBitmap.create(this);
		
		m_ImageView = (ImageView)findViewById(R.id.add_picture);
		String picpath = getIntent().getStringExtra("picpath");
//		Bitmap bmp = BitmapFactory.decodeFile(picpath); 
		
//		BitmapFactory.Options bfOptions=new BitmapFactory.Options();
//		bfOptions.inDither=false;                    
//        bfOptions.inPurgeable=true;              
//        bfOptions.inTempStorage=new byte[12 * 1024]; 
//        
//		 int be = bfOptions.outHeight/40;
//		 if (be <= 0) {
//			 Toast.makeText(this, "be = 10",
//					 Toast.LENGTH_LONG).show();
//			 be = 10;
//		 }
//		 bfOptions.inSampleSize = be;
//        
//        File file = new File(picpath);
//        
//        FileInputStream fs=null;
//        try {
//           fs = new FileInputStream(file);
//       } catch (FileNotFoundException e) {
//           e.printStackTrace();
//       }
//        Bitmap bmp = null;
//        if(fs != null)
//           try {
//               bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }finally{ 
//               if(fs!=null) {
//                   try {
//                       fs.close();
//                   } catch (IOException e) {
//                       e.printStackTrace();
//                   }
//               }
//           }
        
        
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		 options.inJustDecodeBounds = true; // �����˴�����һ��Ҫ�ǵý�ֵ����Ϊfalse
//		 Bitmap bitmap =BitmapFactory.decodeFile(picpath,options);
//		 options.inJustDecodeBounds = false;
//		 int be = options.outHeight/40;
//		 if (be <= 0) {
//			 Toast.makeText(this, "be = 10",
//					 Toast.LENGTH_LONG).show();
//			 be = 10;
//		 }
//		 options.inSampleSize = be;
//		 bitmap = BitmapFactory.decodeFile(picpath,options);
////		 m_ImageView.setImageBitmap(bitmap);
////		 fb.display(m_ImageView,Images.imageUrls[position]);
////		 Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//			fb.display(m_ImageView, picpath, bitmap);
//		String bmpPath = scaleBitmap(bmp);
//		Bitmap bmpThumb = BitmapFactory.decodeFile(bmpPath); 
//		m_ImageView.setImageBitmap();
//		fb.display(m_ImageView, bmpPath, bmpThumb);
		Bitmap bmp = null;
		try{
		 bmp = BitmapUtil.getBitmapByPath(picpath, BitmapUtil.getOptions(picpath), 400, 400); 
		} catch (FileNotFoundException e) {  
			  e.printStackTrace();  
			 }  
		m_ImageView.setImageBitmap(bmp);//.setImageURI(uri);
			
		PrepareUI();
	}
	
	private void PrepareUI() {
		m_WhoseClotheSpinner = (Spinner) findViewById(R.id.whose_clothes);
		m_SelectCategorySpinner = (Spinner) findViewById(R.id.select_category);
		m_PutPositionSpinner = (Spinner) findViewById(R.id.put_position);
		m_SelectSeasonSpinner = (Spinner) findViewById(R.id.select_season);
		m_SelectThicknessSpinner = (Spinner) findViewById(R.id.select_thickness);
		m_SelectStyleSpinner = (Spinner) findViewById(R.id.select_styles);
		m_RemindFrequencySpinner = (Spinner) findViewById(R.id.remind_frequency);
		m_WearPlaceSpinner = (Spinner) findViewById(R.id.wear_place);
		
		PrepareWhoseClothe();
		PrepareSelectCategory();
		PreparePutPosition();
		PrepareSelectSeason();
		PrepareSelectThickness();
		PrepareSelectStyle();
		PrepareRemindFrequency();
		PrepareWearPlace();
		
		SetOkButton();
		SetCancelButton();
	}
	
	private void SetOkButton(){
		ok_btn = (Button) findViewById(R.id.ok_button);
//		ok_btn.setText(R.string.paizhao);
		ok_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
//				if (state.equals(Environment.MEDIA_MOUNTED)) {
//					// Intent intent = new Intent();
//					// intent.setAction(PAIZHAO);//TAKE_PIC
//					// intent.addCategory(CATEGORY_TAKE);
//					// startActivity(intent);
//					// startActivityForResult(intent, 1888);//0
//					Intent intent = new Intent(HomePageFragment.this
//							.getActivity(), SelectPicActivity.class);
//					startActivityForResult(intent, TO_SELECT_PHOTO);
//				}

			}
		});
	}
	
	private void SetCancelButton(){
		cancel_btn = (Button) findViewById(R.id.cancel_button);
//		cancel_btn.setText(R.string.paizhao);
		cancel_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/* 
     * @brief  设置用户选择的数据
     * @param  无
     * @return 无
     * */
	private void PrepareWhoseClothe(){

		personNameItems = PersonInfo.getInstance(this).getAllPersonName();
		personNameItems.add("添加人员");
		String[] personNameItemsArr = CommonFunctionCtrl.trans(personNameItems);
		m_WhoseClotheAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				personNameItemsArr); // android.R.layout.simple_spinner_item
		m_WhoseClotheSpinner.setAdapter(m_WhoseClotheAdapter);  
		m_WhoseClotheSpinner.setOnItemSelectedListener(new WhoseClotheSpinnerListener());
		m_WhoseClotheSpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置分类选择的数据
     * @param  无
     * @return 无
     * */
	private void PrepareSelectCategory(){

		categoryNameItems = CategoryInfo.getInstance(this).getAllCategory();
		categoryNameItems.add("添加分类");
		String[] categoryNameItemsArr = CommonFunctionCtrl.trans(categoryNameItems);
		m_SelectCategoryAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				categoryNameItemsArr); // android.R.layout.simple_spinner_item
		m_SelectCategorySpinner.setAdapter(m_SelectCategoryAdapter);  
		m_SelectCategorySpinner.setOnItemSelectedListener(new SelectCategorySpinnerListener());
		m_SelectCategorySpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择存放位置的数据
     * @param  无
     * @return 无
     * */
	private void PreparePutPosition(){

		locationItems = StorageLocationInfo.getInstance(this).getAllLocation();
		locationItems.add("添加存放位置");
		String[] locationItemsArr = CommonFunctionCtrl.trans(locationItems);
		m_PutPositionAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				locationItemsArr); // android.R.layout.simple_spinner_item
		m_PutPositionSpinner.setAdapter(m_PutPositionAdapter);  
		m_PutPositionSpinner.setOnItemSelectedListener(new PutPositionSpinnerListener());
		m_PutPositionSpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择季节的数据
     * @param  无
     * @return 无
     * */
	private void PrepareSelectSeason(){

		seasonItems = SeasonInfo.getInstance(this).getAllSeason();
		seasonItems.add("添加季节");
		String[] seasonItemsArr = CommonFunctionCtrl.trans(seasonItems);
		m_SelectSeasonAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				seasonItemsArr); // android.R.layout.simple_spinner_item
		m_SelectSeasonSpinner.setAdapter(m_SelectSeasonAdapter);  
		m_SelectSeasonSpinner.setOnItemSelectedListener(new SelectSeasonSpinnerListener());
		m_SelectSeasonSpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择厚度的数据
     * @param  无
     * @return 无
     * */
	private void PrepareSelectThickness(){

		thicknessItems = ThicknessInfo.getInstance(this).getAllThickness();
		thicknessItems.add("添加人员");
		String[] thicknessItemsArr = CommonFunctionCtrl.trans(thicknessItems);
		m_SelectThicknessAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				thicknessItemsArr); // android.R.layout.simple_spinner_item
		m_SelectThicknessSpinner.setAdapter(m_SelectThicknessAdapter);  
		m_SelectThicknessSpinner.setOnItemSelectedListener(new SelectThicknessSpinnerListener());
		m_SelectThicknessSpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择风格的数据
     * @param  无
     * @return 无
     * */
	private void PrepareSelectStyle(){

		styleItems = StyleInfo.getInstance(this).getAllStyle();
		styleItems.add("添加风格");
		String[] styleItemsArr = CommonFunctionCtrl.trans(styleItems);
		m_SelectStyleAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				styleItemsArr); // android.R.layout.simple_spinner_item
		m_SelectStyleSpinner.setAdapter(m_SelectStyleAdapter);  
		m_SelectStyleSpinner.setOnItemSelectedListener(new SelectStyleSpinnerListener());
		m_SelectStyleSpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择提醒频率的数据
     * @param  无
     * @return 无
     * */
	private void PrepareRemindFrequency(){

		remindFrequencyItems = CategoryArchiveInfo.getInstance(this).getAllRemindFrequency();
		remindFrequencyItems.add("添加提醒频率");
		String[] remindFrequencyItemsArr = CommonFunctionCtrl.trans(remindFrequencyItems);
		m_RemindFrequencyAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				remindFrequencyItemsArr); // android.R.layout.simple_spinner_item
		m_RemindFrequencySpinner.setAdapter(m_RemindFrequencyAdapter);  
		m_RemindFrequencySpinner.setOnItemSelectedListener(new RemindFrequencySpinnerListener());
		m_RemindFrequencySpinner.setVisibility(View.VISIBLE); 
	}
	
	/* 
     * @brief  设置选择穿衣场合的数据
     * @param  无
     * @return 无
     * */
	private void PrepareWearPlace(){

		wearPlaceItems = WearPlaceInfo.getInstance(this).getAllWearPlace();
		wearPlaceItems.add("添加穿衣场合");
		String[] wearPlaceItemsArr = CommonFunctionCtrl.trans(wearPlaceItems);
		m_WearPlaceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				wearPlaceItemsArr); // android.R.layout.simple_spinner_item
		m_WearPlaceSpinner.setAdapter(m_WearPlaceAdapter);  
		m_WearPlaceSpinner.setOnItemSelectedListener(new WearPlaceSpinnerListener());
		m_WearPlaceSpinner.setVisibility(View.VISIBLE); 
	}
	
	private String scaleBitmap(Bitmap bitmapOrg){
		//获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        
        //定义预转换成的图片的宽度和高度
        int newWidth = 200;
        int newHeight = 200;
        
        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        
        //旋转图片 动作
//        matrix.postRotate(45);
        
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
        width, height, matrix, true);
        PictureOperatingCtrl poc = new PictureOperatingCtrl();
		return poc.SavePicInLocal(resizedBitmap);// 保存到本地
        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
//        return resizedBitmap;
	}
}
