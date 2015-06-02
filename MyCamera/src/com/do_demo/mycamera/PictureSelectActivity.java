package com.do_demo.mycamera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class PictureSelectActivity extends Activity implements OnClickListener {
	private Context context = this;
	private LinearLayout cameraBtn;
	private LinearLayout albumBtn;
	private Uri uri;
	private String path;
	private Intent intent;

	private static final int CAMERA = 0;
	private static final int ALBUM = 1;

	public static Dialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_select);

		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		loadingDialog = new Dialog(context, R.style.LoadingDialog);
		loadingDialog.setContentView(R.layout.dialog_progressbar);
		cameraBtn = (LinearLayout) findViewById(R.id.camera_btn);
		albumBtn = (LinearLayout) findViewById(R.id.album_btn);
		cameraBtn.setOnClickListener(this);
		albumBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 打开系统相机
		case R.id.camera_btn:
			try {
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				uri = getUri();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, CAMERA);
			} catch (Exception e) {

			}
			break;

		// 打开系统相册
		case R.id.album_btn:
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, ALBUM);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取文件的Uri
	 * 
	 * @return
	 */
	private static Uri getUri() {
		// 目录
		File dir = null;
		try {
			dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return null;
			}
		}

		// 时间戳
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		// 文件以IMG_+时间戳命名
		File mediaFile = new File(dir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		return Uri.fromFile(mediaFile);
	}

	/**
	 * 返回结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == CAMERA && resultCode == RESULT_OK) {
			loadingDialog.show();
			path = uri.getPath();
			goToMain();
		}

		if (requestCode == ALBUM && resultCode == RESULT_OK && data != null) {
			loadingDialog.show();
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			path = cursor.getString(columnIndex);
			cursor.close();
			goToMain();
		}
	}

	/**
	 * 进入主菜单
	 */
	private void goToMain() {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("path", path);
		startActivity(intent);
	}
}
