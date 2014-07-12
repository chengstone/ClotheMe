package com.iamyjx.takesave_picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.example.clotheme.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity implements 
        CameraPreview.OnCameraStatusListener { 
 
    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
    public static final String PATH = Environment.getExternalStorageDirectory() 
            .toString() + "/AndroidMedia/"; 
 
    private CameraPreview mCameraPreview; 
    private ImageView focusView; 
    private boolean isTaking = false; // ������ 
    private boolean isAutoFocus;//�Ƿ��Զ��Խ� 
 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        // ���ú��� 
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        // ����ȫ�� 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        // ���ò�����ͼ 
        setContentView(R.layout.activity_camera); 
        // ����Ԥ������ 
        mCameraPreview = (CameraPreview) findViewById(R.id.preview); 
        mCameraPreview.setOnCameraStatusListener(this); 
        // ����ͼƬ 
        focusView = (ImageView) findViewById(R.id.focusView); 
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(this) ;
        isAutoFocus= prefs.getBoolean("preferences_autoFocus",false);
    } 
 
    /** 
     * �����¼� 
     */ 
    @Override 
    public boolean onTouchEvent(MotionEvent event) { 
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isTaking) { 
            isTaking = true; 
            mCameraPreview.takePicture(isAutoFocus); 
        } 
        return super.onTouchEvent(event); 
    } 
 
    /** 
     * �洢ͼ�񲢽���Ϣ�����ý����ݿ� 
     */ 
    private Uri insertImage(ContentResolver cr, String name, long dateTaken, 
            String directory, String filename, Bitmap source, byte[] jpegData) { 
 
        OutputStream outputStream = null; 
        String filePath = directory + filename; 
        try { 
            File dir = new File(directory); 
            if (!dir.exists()) { 
                dir.mkdirs(); 
            } 
            File file = new File(directory, filename); 
            if (file.createNewFile()) { 
                outputStream = new FileOutputStream(file); 
                if (source != null) { 
                    source.compress(CompressFormat.JPEG, 75, outputStream); 
                } else { 
                    outputStream.write(jpegData); 
                } 
            } 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
            return null; 
        } catch (IOException e) { 
            e.printStackTrace(); 
            return null; 
        } finally { 
            if (outputStream != null) { 
                try { 
                    outputStream.close(); 
                } catch (Throwable t) { 
                } 
            } 
        } 
        ContentValues values = new ContentValues(7); 
        values.put(MediaStore.Images.Media.TITLE, name); 
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename); 
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken); 
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); 
        values.put(MediaStore.Images.Media.DATA, filePath); 
        return cr.insert(IMAGE_URI, values); 
    } 
 
    /** 
     * ������ս����¼� 
     */ 
    @Override 
    public void onCameraStopped(byte[] data) { 
        Log.e("onCameraStopped", "==onCameraStopped=="); 
        // ����ͼ�� 
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); 
        // ϵͳʱ�� 
        long dateTaken = System.currentTimeMillis(); 
        // ͼ����� 
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken) 
                .toString() + ".jpg"; 
        // �洢ͼ��PATHĿ¼�� 
        Uri uri = insertImage(getContentResolver(), filename, dateTaken, PATH, 
                filename, bitmap, data); 
        // ���ؽ�� 
        Intent intent = getIntent(); 
        intent.putExtra("uriStr", uri.toString()); 
        intent.putExtra("dateTaken", dateTaken); 
        // intent.putExtra("filePath", PATH + filename); 
        // intent.putExtra("orientation", orientation); // ���㷽�� 
        setResult(20, intent); 
        // �رյ�ǰActivity 
        finish(); 
    } 
 
    /** 
     * ����ʱ�Զ��Խ��¼� 
     */ 
    @Override 
    public void onAutoFocus(boolean success) { 
        // �ı�Խ�״̬ͼ�� 
        if (success) { 
            focusView.setImageResource(R.drawable.right); 
        } else { 
            focusView.setImageResource(R.drawable.wrong); 
            Toast.makeText(this, "���಻׼�������ģ�", Toast.LENGTH_SHORT).show(); 
            isTaking = false; 
        } 
    } 
} 