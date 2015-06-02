package com.do_demo.mycamera.util;




import android.annotation.SuppressLint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class TouchEvent2 implements OnTouchListener{
	OnClickListener		mOnClickListener;
	OnLongClickListener	mOnLongClickListener;
	private View view;
	private int			start_x, start_y, current_x, current_y;								// 触摸位置

	/** 防止onClick、LongClick跟缩放动作一起触发 */
	@SuppressWarnings("deprecation")
	GestureDetector		mGestureDetector	= new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

												@Override
												public boolean onDown(MotionEvent e) {
													onTouchDown(e);
													return false;
												}

												@Override
												public void onLongPress(MotionEvent e) {
													/*if (mOnLongClickListener != null)
														mOnLongClickListener.onLongClick(TouchView.this);*/
												}

												@Override
												public boolean onSingleTapConfirmed(MotionEvent e) {
											/*		if (mOnClickListener != null)
														mOnClickListener.onClick(TouchView.this);*/
													return false;
												}

												@Override
												public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
													//单指的计算
													if(e2.getPointerCount()==1){
														onTouchMove(e2);
													}
												
													return false;
												}
											});
	
	/** 移动的处理 **/
	@SuppressLint("NewApi")
	void onTouchMove(MotionEvent event) {
		int left = 0, top = 0, right = 0, bottom = 0;
		
		/** 获取相应的l，t,r ,b **/
		left = current_x - start_x;
		right = current_x + view.getWidth() - start_x;
		top = current_y - start_y;
		bottom = current_y - start_y + view.getHeight();
		
		current_x = (int) event.getRawX();
		current_y = (int) event.getRawY();

	Log.i("滑动:", "left:"+left+"/t top"+top);
	Log.i("滑动:", "current_x:"+current_x+"/t current_y"+current_y);
	
	view.setX(left);
	view.setY(top-200);

	}
	
	/** 按下 **/
	void onTouchDown(MotionEvent event) {


		current_x = (int) event.getRawX();
		current_y = (int) event.getRawY();

		start_x = (int) event.getX();
		start_y = (int) event.getY();
		

	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(view==null){
			view=v;
		}
		// 因为onTouchEvent()不知道 长时间移动或双手指缩放和长按事件的区别，所以配合GestureDetector来处理事件
				mGestureDetector.onTouchEvent(event);
			
			
				/** 处理单点、多点触摸 **/
				switch (event.getAction() & MotionEvent.ACTION_MASK) {

				// 多点触摸
				case MotionEvent.ACTION_POINTER_DOWN:
				//	onPointerDown(event);
					
					break;

				case MotionEvent.ACTION_UP:

				//	mode = MODE.NONE;
					break;

				// 多点松开
				case MotionEvent.ACTION_POINTER_UP:

			/*		mode = MODE.NONE;
					*//** 执行缩放还原 **//*
					if (isScaleAnim)
						doScaleAnim();*/

					break;
				}

				return true;
	}

}
