package com.activity.clotheme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.clotheme.R;

public class ImagePreviewActivity extends Activity{
	private ImageView m_ImageView = null;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagepreview);
		m_ImageView = (ImageView)findViewById(R.id.add_picture);
		String picpath = getIntent().getStringExtra("picpath");
		Bitmap bmp = BitmapFactory.decodeFile(picpath); 
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		 options.inJustDecodeBounds = true; // �����˴�����һ��Ҫ�ǵý�ֵ����Ϊfalse
		 Bitmap bitmap =BitmapFactory.decodeFile(picpath,options);
		 options.inJustDecodeBounds = false;
		 int be = options.outHeight/40;
		 if (be <= 0) {
			 be = 10;
		 }
		 options.inSampleSize = be;
		 bitmap = BitmapFactory.decodeFile(picpath,options);
		 m_ImageView.setImageBitmap(bitmap);
		 
			
//			m_ImageView.setImageBitmap(bmp);//.setImageURI(uri);
			
	}
}
