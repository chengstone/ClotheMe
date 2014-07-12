package com.iamyjx.takesave_picture;


import com.example.clotheme.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	//���Ǵ����˵�
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //�˵�ѡ�ѡ��ʱ
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      switch (item.getItemId()) {
        case R.id.action_settings:
          intent=new Intent(this, SettingActivity.class);
          startActivity(intent);
          break;
        default:
          return super.onOptionsItemSelected(item);
      }
      return true;
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (2 == requestCode) { // �Զ������ش���
			// ���ճɹ�����Ӧ����20
			if (resultCode == 20) {
				Bundle bundle = data.getExtras();
				// �����Ƭuri
				Uri uri = Uri.parse(bundle.getString("uriStr"));
				// �������ʱ��
				long dateTaken = bundle.getLong("dateTaken");
				try {
					// ��ý����ݿ��ȡ����Ƭ
					Bitmap cameraBitmap = MediaStore.Images.Media.getBitmap(
							getContentResolver(), uri);
					previewBitmap(cameraBitmap); // Ԥ��ͼ��
					// ��ý����ݿ�ɾ�����Ƭ��������ʱ�䣩
					getContentResolver().delete(
							CameraActivity.IMAGE_URI,
							MediaStore.Images.Media.DATE_TAKEN + "="
									+ String.valueOf(dateTaken), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// takeBtn2.setClickable(true);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startCamera(View view) {
		// �����Զ������
		Intent intent = new Intent(this, CameraActivity.class);
		startActivityForResult(intent, 2);

	}

	private void previewBitmap(Bitmap bitmap) {
//		ImageView view = (ImageView) findViewById(R.id.previewBitmap);
//		view.setImageBitmap(bitmap);
	}
}
