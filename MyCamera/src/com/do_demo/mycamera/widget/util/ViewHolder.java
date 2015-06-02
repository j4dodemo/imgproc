package com.do_demo.mycamera.widget.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 将ListView的Adapter里的方法进行封装，减少代码量
 * @author 
 * @version 1.0
 * @created 2015-04-19
 */
public class ViewHolder {
	private SparseArray<View> mViews;
	private View mConVertView;

	public ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mViews = new SparseArray<View>();
		mConVertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConVertView.setTag(this);
	}

	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			return holder;
		}
	}

	/**
	 * 通过viewId获取控件
	 * 
	 * @param viewId
	 * @return
	 */
	public View getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConVertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return view;
	}

	public View getConVertView() {
		return mConVertView;
	}

}
