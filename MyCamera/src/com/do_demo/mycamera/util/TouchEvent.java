package com.do_demo.mycamera.util;

import com.do_demo.mycamera.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class TouchEvent implements OnTouchListener {
	private OnActionDownListener listener;

	float x_down = 0;
	float y_down = 0;

	PointF start = new PointF();
	PointF mid = new PointF();

	float oldDist = 1f;
	float oldRotation = 0;
	float nextRotation = 0;

	int iv_width = 0;
	int iv_height = 0;

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	int mode = NONE;

	View view;// 当前元素
	ViewGroup parent_view;// 顶级窗口
	ImageView iv;// 子图控件
	
	public TouchEvent(View v, ImageView img, ViewGroup pview,OnActionDownListener listener) {
		view = v;
		iv = img;
		iv_width = iv.getDrawable().getIntrinsicWidth();
		iv_height = iv.getDrawable().getIntrinsicHeight();
		parent_view = pview;
		setViewSize(1);
		
		setOnActionDownListener(listener);
	}

	/***
	 * 
	 * @Description: 求弦长
	 * @date 2015年4月24日 上午10:37:58
	 * @param max_val
	 * @return
	 */
	public double Diagonal(double max_val) {
		return Math.sqrt(2 * max_val * max_val);
	}

	@SuppressLint("NewApi")
	private boolean setViewSize(float scale) {

		double max_val = Math.max(iv_width * scale, iv_height * scale);

		max_val = Diagonal(max_val);

		double minCritical = Diagonal(Math.max(iv.getDrawable().getIntrinsicWidth(), iv.getDrawable().getIntrinsicHeight()));

		double mx_view_w = parent_view.getWidth();
		double mx_view_h = parent_view.getHeight();
		double maxCritical = Math.min(mx_view_w, mx_view_h);

		if (max_val < minCritical) {
			return false;
		}

		if (max_val > maxCritical) {
			max_val = Math.min(mx_view_h, mx_view_w);
			view.setMinimumWidth((int) max_val);
			view.setMinimumHeight((int) max_val);

			return false;
		}

		view.setMinimumWidth((int) max_val);
		view.setMinimumHeight((int) max_val);

		return true;

	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {

		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		// Log.i("", "x1-->:"+event.getX(1)+",x2-->:"+event.getRawX());

		return FloatMath.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// 取旋转角度
	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		view.bringToFront();

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			x_down = event.getRawX() - view.getX();
			y_down = event.getRawY() - view.getY();

			if (listener != null) {
				listener.onActionDown();
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			oldDist = spacing(event);

			oldRotation = rotation(event);

			nextRotation = iv.getRotation();
			iv_width = iv.getWidth();
			iv_height = iv.getHeight();

			midPoint(mid, event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {

				// Log.i("", "x0:"+event.getX(0) +"x1:"+event.getX(1));

				float rotation = rotation(event) - oldRotation;

				float newDist = spacing(event);

				float scale = newDist / oldDist;

				moveView(-1, -1, scale, rotation);

			} else if (mode == DRAG) {

				// Log.i("","起点x:"event.getRawX()-x_down+"     终点:"+event.getX());
				moveView(event.getRawX() - x_down, event.getRawY() - y_down, -1, -1);

			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		}

		return true;
	}

	@SuppressLint("NewApi")
	private void moveView(float x, float y, float scale, float rotation) {
		if (x != -1)
			view.setX(x);
		if (y != -1)
			view.setY(y);
		if (scale != -1) {

			if (setViewSize(scale)) {
				iv.setMinimumWidth(Math.round(iv_width * scale));
				iv.setMinimumHeight(Math.round(iv_height * scale));
			}
			/*
			 * view.setScaleX(scale); view.setScaleY(scale);
			 */
		}

		if (rotation != -1) {

			iv.setRotation(nextRotation + rotation);
			
		}

	}

	public interface OnActionDownListener {
		public void onActionDown();
	}

	public void setOnActionDownListener(OnActionDownListener listener) {
		this.listener = listener;
	}
}
