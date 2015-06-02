package com.do_demo.mycamera.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.view.Window;

public class ScreenShot {

	private static Bitmap takeScreenShot(Activity activity) {
		return takeScreenShot(activity, 0, 0);
	}
	// 获取指定Activity的截屏，保存到png文件
	private static Bitmap takeScreenShot(Activity activity, int x, int y) {

		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		// int displayWidth = dm.widthPixels;
		// int displayHeight = dm.heightPixels;

		int contentTop = activity.getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// statusBarHeight是上面所求的状态栏的高度
		int titleBarHeight = contentTop - statusBarHeight;
		y += titleBarHeight + statusBarHeight;

		int bHeight = b1.getHeight();
		int nHeight = height - y;
		if ((y + nHeight) > bHeight) {
			nHeight = bHeight - y;
		}
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, x, y, width, nHeight);
		view.destroyDrawingCache();
		// 问题
		// if (y + height > source.getHeight()) {
		// throw new
		// IllegalArgumentException("y + height must be <= bitmap.height()");
		// }
		return b;
	}
	private static Bitmap takeScreenShot(View v, int x, int y) {
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();
		Bitmap b1 = v.getDrawingCache();
		// 获取屏幕长和高
		int width = v.getWidth() - 2 * x;
		int height = v.getHeight() - 2 * y;
		Bitmap b = Bitmap.createBitmap(b1, x, y, width, height);
		v.destroyDrawingCache();
		return b;
	}
	// 保存到sdcard
	private static void savePic(Bitmap b, String strFileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(strFileName);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static Bitmap takeScreenShot(View v) {
		return takeScreenShot(v, 0, 0);
	}

	/**
	 * 截屏
	 * 
	 * @param a
	 *            Activity
	 * @param filePath
	 *            保存路径
	 */
	public static void shoot(Activity a, String filePath) {
		Bitmap bitmap = ScreenShot.takeScreenShot(a);
		ScreenShot.savePic(bitmap, filePath);
	}

	/**
	 * 根据Activity截屏
	 * 
	 * @param a
	 *            Activity
	 * @param filePath
	 *            保存路径
	 */
	public static void shoot(Activity a, String filePath, int x, int y) {
		ScreenShot.savePic(ScreenShot.takeScreenShot(a, x, y), filePath);
	}
	/**
	 * 根据Activity截屏
	 * 
	 * @param a
	 * @param filePath
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static void shoot(Activity a, String filePath, int x, int y, int w,
			int h) {
		Bitmap bitmap = ScreenShot.takeScreenShot(a, x, y);
		Bitmap newBitmap = bitmap;
		try {
			newBitmap = ImageUtils.createImageThumbnail(bitmap, w, h);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScreenShot.savePic(newBitmap, filePath);
	}
	/**
	 * 根据x和y坐标对v进行居中截屏
	 * 
	 * @param v
	 * @param filePath
	 * @param x
	 * @param y
	 */
	public static void shoot(View v, String filePath, int x, int y) {
		ScreenShot.savePic(ScreenShot.takeScreenShot(v, x, y), filePath);
	}
	/**
	 * 根据x和y坐标对v进行居中截屏
	 * 
	 * @param v
	 * @param filePath
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static void shoot(View v, String filePath, int x, int y, int w, int h) {
		Bitmap bitmap = ScreenShot.takeScreenShot(v, x, y);
		Bitmap newBitmap = bitmap;
		try {
			newBitmap = ImageUtils.createImageThumbnail(bitmap, w, h);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScreenShot.savePic(newBitmap, filePath);
	}

	/**
	 * 截屏
	 * 
	 * @param v
	 *            View
	 * @param filePath
	 *            保存路径
	 */
	public static void shoot(View v, String filePath) {
		ScreenShot.savePic(ScreenShot.takeScreenShot(v), filePath);
	}

}