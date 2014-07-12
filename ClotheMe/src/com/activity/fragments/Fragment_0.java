package com.activity.fragments;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.common.clothe.CommonDefine;
import com.example.clotheme.R;
import com.functionCtrl.clotheme.PictureOperatingCtrl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;  
import android.os.Environment;
import android.support.v4.app.Fragment;  
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.Button;
import android.widget.Toast;
  
public class Fragment_0 extends Fragment   
{  
	private Bitmap photo = null;  
    private String fileName;  
    private static String picturepath = CommonDefine.CommonString.PICTUREPATH;
    
    Button btn;
    public final static String TAKE_PIC = "android.media.action.IMAGE_CAPTURE";
    public final static String CATEGORY_TAKE = "com.clotheme.intent.category.TAKE_PIC";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)   
    {  
        View mView=inflater.inflate(R.layout.fragment_homepage, container, false);  
        if(mView != null){
        btn = (Button)mView.findViewById(R.id.button1);
        btn.setText(R.string.paizhao);
        btn.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		String state = Environment.getExternalStorageState(); // 判断是否存在sd卡  
                if (state.equals(Environment.MEDIA_MOUNTED)) { 
                	Intent intent = new Intent();
                	intent.setAction(TAKE_PIC);
//        			intent.addCategory(CATEGORY_TAKE);
//        			startActivity(intent);
                	startActivityForResult(intent, 0); 
                }

        	}
        });
        }
        return mView;  
    }  
      
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
        try {  
            if (requestCode != 0) {  
                return;  
            }  
            super.onActivityResult(requestCode, resultCode, data);  
            // 两种方式获得拍摄照片的返回值  
            Uri uri = data.getData();  
            if (uri != null) {  
                photo = BitmapFactory.decodeFile(uri.getPath());  
            }  
            if (photo == null) {  
                Bundle bundle = data.getExtras();  
                if (bundle != null) {  
                    photo = (Bitmap) bundle.get("data");  
                    SavePicInLocal(photo);// 保存到本地  
  
                    // 生成一个Intent对象  
//                    Intent intent = new Intent();  
//                    intent.putExtra("picpath", fileName);  
//                    intent.setClass(Fragment_0.this.getActivity(), JcjlEditActivity.class);  
//                    // 启动intent的Activity  
//                    TackPhoto.this.startActivity(intent);  
                } else {  
                    Toast.makeText(Fragment_0.this.getActivity(), "未拍摄照片", Toast.LENGTH_LONG)  
                            .show();  
                    // 生成一个Intent对象  
//                    Intent intent = new Intent();  
//                    intent.setClass(TackPhoto.this, JcjlEditActivity.class);  
//                    TackPhoto.this.startActivity(intent);  
                }  
            }  
  
        } catch (Exception e) {  
  
        }  
    }  
  
    // 保存拍摄的照片到手机的sd卡  
    private void SavePicInLocal(Bitmap bitmap) {  
        FileOutputStream fos = null;  
        BufferedOutputStream bos = null;  
        ByteArrayOutputStream baos = null; // 字节数组输出流  
        try {  
            baos = new ByteArrayOutputStream();  
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
            byte[] byteArray = baos.toByteArray();// 字节数组输出流转换成字节数组  
            String saveDir = Environment.getExternalStorageDirectory() + "/ClotheMe";  
            File dir = new File(saveDir);  
            if (!dir.exists()) {  
                dir.mkdir(); // 创建文件夹  
            }  
            fileName = saveDir + "/" + System.currentTimeMillis() + ".jpg";//".PNG";  
            File file = new File(fileName);  
            file.delete();  
            if (!file.exists()) {  
                file.createNewFile();// 创建文件  
                Log.e("PicDir", file.getPath());  
                Toast.makeText(Fragment_0.this.getActivity(), fileName, Toast.LENGTH_LONG)  
                        .show();  
            }  
            // 将字节数组写入到刚创建的图片文件中  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(byteArray);  
            
//            Bitmap miniThumb = PictureOperatingCtrl.extractMiniThumb(file, width, height);
  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        } finally {  
            if (baos != null) {  
                try {  
                    baos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
  
        }  
  
    }  
    
    private String getPictureFilepath(){  
        return String.format(picturepath, Fragment_0.this.getActivity().getApplicationInfo().packageName);  
    }
}  