package com.do_demo.mycamera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class BitmapHelper {
	
public static 	Bitmap getBitmap(String pathName){
		Options opt=	 new Options();
		opt.inJustDecodeBounds = true;
	
		Log.d("do_demo", opt.outWidth + "," + opt.outHeight);
		opt.inSampleSize = 10;
		
		Bitmap btmp=	BitmapFactory.decodeFile(pathName,opt);
		return btmp;
	}

}
