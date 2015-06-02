package com.do_demo.mycamera.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.do_demo.mycamera.util.GPUImageFilterTools.FilterAdjuster;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImage.ResponseListener;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;

public class LomoFilter  {

/*	@Override
	public void show(GPUImageView imgView, Bitmap bt) {
		// TODO Auto-generated method stub
		imgView.setImage(bt);
		GPUImageFilter mFilter =new GPUImagePixelationFilter();//new GPUImageGammaFilter(); //
		imgView.setFilter(mFilter);
	FilterAdjuster mFilterAdjuster = new FilterAdjuster(mFilter);
		mFilterAdjuster.adjust(60);

		imgView.requestRender();

		 mFilter =new GPUImageBrightnessFilter(); //new GPUImagePixelationFilter();
			imgView.setFilter(mFilter);
			 mFilterAdjuster = new FilterAdjuster(mFilter);
				mFilterAdjuster.adjust(60);

				imgView.requestRender();
		
	}*/


	public Bitmap show(Context context,Bitmap bt) {
		// TODO Auto-generated method stub
		GPUImage img=new GPUImage(context);
		img.setImage(bt);
		
/*		GPUImagePixelationFilter mFilter =new GPUImagePixelationFilter();
		mFilter.setPixel(30);
		img.setFilter(mFilter);
*/
		InputStream is = null;
		AssetManager as = context.getAssets();
		GPUImageToneCurveFilter filter = new GPUImageToneCurveFilter();
		long t=System.currentTimeMillis();
		try {
		
		
		    is = as.open("1977.acv");
	

		    filter.setFromCurveFileInputStream(is);

		    is.close();
		} catch (Exception e) {
		  
		}
		
	
		img.setFilter(filter);


		bt=img.getBitmapWithFilterApplied();

			System.out.println("输出"+(System.currentTimeMillis()-t));

		return  bt;
		
		
		
	}

}
