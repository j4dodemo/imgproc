package com.do_demo.mycamera.util;






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
import android.widget.ImageView;

public class TouchEvent3 implements OnTouchListener{

	float x_down = 0;
	float y_down = 0;

	
	PointF start = new PointF();
	PointF mid = new PointF();

	float oldDist = 1f;
	float oldRotation = 0;
	float nextRotation = 0;


	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	int mode = NONE;



View view;







	// 触碰两点间距离
	private float spacing(MotionEvent event) {

		
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		Log.i("", "x1-->:"+event.getX(1)+",x2-->:"+event.getRawX());
		
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

		
		if(view==null){
			view= v;
		}
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			x_down = event.getX();

			y_down =event.getRawY()-view.getY();
		

		
			
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			oldDist = spacing(event);
			Log.i("缩放距离1:", "oldDist:"+oldDist);
			oldRotation = rotation(event);

			midPoint(mid, event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {

				float rotation = rotation(event) - oldRotation;
				
				float newDist = spacing(event);
				Log.i("缩放距离2:", "newDist:"+newDist);
				float scale = newDist / oldDist;

				

			

		

			} else if (mode == DRAG) {

			
				//Log.i("","起点x:"event.getRawX()-x_down+"     终点:"+event.getX());
				moveView(event.getRawX()-x_down, event.getRawY()-y_down, -1, -1);
			



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
	private void moveView(float x,float y,float scale ,float rotation) {
		// TODO Auto-generated method stub
	
		if(x!=-1)
		view.setX(x);
		if(y!=-1)
		view.setY(y);
		if(scale!=-1){
			view.setScaleX(scale);
		view.setScaleY(scale);
		}

		if(rotation!=-1)
		view.setRotation(rotation);

	}


}
