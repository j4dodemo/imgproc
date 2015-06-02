package com.do_demo.mycamera.util;

import android.widget.ImageView;

/**
 * 全局变量
 * 
 * @author Hzp
 * @data 2015-4-27 上午11:45:42
 */
public class AppVar {
	private static AppVar appVar;

	// 上一个删除按钮
	private ImageView lastRemoveBtn;

	// 上一个选择的滤镜
	private ImageView lastSelected;

	private AppVar() {

	}

	public static AppVar getInstance() {
		if (appVar == null) {
			appVar = new AppVar();
		}
		return appVar;
	}

	public ImageView getLastRemoveBtn() {
		return lastRemoveBtn;
	}

	public void setLastRemoveBtn(ImageView lastRemoveBtn) {
		this.lastRemoveBtn = lastRemoveBtn;
	}

	public ImageView getLastSelected() {
		return lastSelected;
	}

	public void setLastSelected(ImageView lastSelected) {
		this.lastSelected = lastSelected;
	}

}
