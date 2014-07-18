package com.activity.clotheme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.activity.fragments.HomePageFragment;
import com.example.clotheme.R;
import com.functionCtrl.clotheme.BitmapUtil;
import com.functionCtrl.clotheme.PictureOperatingCtrl;

public class ImagePreviewActivity extends Activity{
	private ImageView m_ImageView = null;
	private FinalBitmap fb;
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
