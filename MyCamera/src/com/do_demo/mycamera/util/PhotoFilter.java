package com.do_demo.mycamera.util;

import java.io.InputStream;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class PhotoFilter {
	public static final String FILTER_006="006_tone";
	public static final String FILTER_008="008_tone";
	public static final String FILTER_010="010_tone";
	public static final String FILTER_HISTORY="1977";
	public static final String FILTER_BARANNAN="Brannan";
	public static final String FILTER_C1="Curves1";
	public static final String FILTER_C2="Curves2";
	public static final String FILTER_C3="Curves3";
	public static final String FILTER_GOTHAM="Gotham";
	public static final String FILTER_HEFE="Hefe";
	public static final String FILTER_LOMO="lomo";
	public static final String FILTER_LORD_KELVIN="Lord Kelvin";
	public static final String FILTER_NASHVILLE="Nashville";
	public static final String FILTER_C4="tone_cuver_sample";
	public static final String FILTER_X_PRO="X-PRO II";

	
	public static Bitmap getBitMap(Context context,Bitmap bt,String filterName){
		
		
/*		GPUImagePixelationFilter mFilter =new GPUImagePixelationFilter();
		mFilter.setPixel(30);
		img.setFilter(mFilter);
*/

		try {
			GPUImage img=new GPUImage(context);
			img.setImage(bt);
			InputStream is = null;
			AssetManager as = context.getAssets();
			GPUImageToneCurveFilter filter = new GPUImageToneCurveFilter();
			long t=System.currentTimeMillis();
		
		    is = as.open(filterName+".acv");
	

		    filter.setFromCurveFileInputStream(is);

		    is.close();

		
	
	img.setFilter(filter);


		bt=img.getBitmapWithFilterApplied();

Toast.makeText(context, "滤镜输出消耗时间:"+(System.currentTimeMillis()-t), 1).show();
			//Log.i("","滤镜输出消耗时间:"+(System.currentTimeMillis()-t));

		} catch (Exception e) {
			bt=null;
		}
		
		
		return bt;
	}

}
