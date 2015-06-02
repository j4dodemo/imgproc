package com.do_demo.mycamera.util;

import com.do_demo.mycamera.R;
import com.do_demo.mycamera.util.TouchEvent.OnActionDownListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Layout;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnHoverListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class StickersHelper {
	public static final int STICKERS_1 = R.drawable.ic_launcher;
	public static final int STICKERS_2 = R.drawable.av;

	public static void addStickers(Activity activity, final ViewGroup parent_view, int src_stickers) {
		addStickers(activity, parent_view, src_stickers, null, null);
	}

	/***
	 * 
	 * @Description: 增加贴纸
	 * @date 2015年4月22日 上午9:50:15
	 * @param activity
	 * @param parent_view
	 * @param src_stickers
	 */
	@SuppressLint("NewApi")
	public static void addStickers(Activity activity, final ViewGroup parent_view, int src_stickers, Integer w, Integer h) {
		// img_lot.removeViewAt(1);
		/*
		 * child.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @SuppressLint("NewApi")
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
		 * Auto-generated method stub // v.setPadding(100, 100, 0, 0);
		 * v.setX(400.2f); v.setScaleX(1.5f); v.setRotation(45.5f); return
		 * false;
		 * 
		 * } });
		 */
		LayoutInflater inflater = activity.getLayoutInflater();
		final View child = inflater.inflate(R.layout.lay_img, null);
		ImageView img = (ImageView) child.findViewById(R.id.img_src);
		final ImageView btn_remove = (ImageView) child.findViewById(R.id.btn_remove);
		img.setImageResource(src_stickers);

		child.setOnTouchListener(new TouchEvent(child, img, parent_view, new OnActionDownListener() {
			// 触摸控件时回调此方法
			@Override
			public void onActionDown() {
				// 显示当前触摸控件的x,隐藏上一个触摸控件的x
				btn_remove.setVisibility(View.VISIBLE);
				ImageView lastRemoveBtn = AppVar.getInstance().getLastRemoveBtn();
				if (lastRemoveBtn != null && !lastRemoveBtn.equals(btn_remove)) {
					lastRemoveBtn.setVisibility(View.GONE);
				}
				AppVar.getInstance().setLastRemoveBtn(btn_remove);
			}
		}));

		// img.setOnTouchListener(new TouchEvent());

		/*
		 * parent_view.setFocusable(true);
		 * parent_view.setFocusableInTouchMode(true);
		 * 
		 * parent_view.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View arg0, boolean hasFocus) {
		 * Log.i("youdjiao", "================>"); // TODO Auto-generated method
		 * stub if(hasFocus){ btn_remove.setVisibility(View.VISIBLE); }else{
		 * btn_remove.setVisibility(View.INVISIBLE); }
		 * 
		 * } });
		 */

		btn_remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				parent_view.removeView(child);
			}
		});

		if (w != null && h != null) {
			Log.i("", "getWidth" + child.getWidth());
			Log.i("", "img" + img.getWidth());

			img.setMinimumWidth(w);
			img.setMinimumHeight(h);

		}
		parent_view.addView(child);

	}

	@SuppressLint("NewApi")
	public static void addBackground(Activity activity, final ViewGroup parent_view, int src_stickers) {

		if (src_stickers == R.drawable.edit_drag_cancel) {
			parent_view.removeAllViews();
			return;
		}

		LayoutInflater inflater = activity.getLayoutInflater();
		final View child = inflater.inflate(R.layout.lay_src_bg, null);
		// child.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		// child.setOnTouchListener(new TouchEvent());
		ImageView img = (ImageView) child.findViewById(R.id.img_src);

		img.setImageResource(src_stickers);

		parent_view.removeAllViews();
		parent_view.addView(child);

		// return img.getScaleX();

	}

}
