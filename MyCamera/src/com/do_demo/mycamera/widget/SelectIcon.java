package com.do_demo.mycamera.widget;

import com.do_demo.mycamera.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SelectIcon extends ImageView {
	private int circleColor;// 外圆颜色
	private int tickColor;// 勾的颜色
	private int tickWidth;// 勾的宽度

	public SelectIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getValues(context, attrs);
	}

	public SelectIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
		getValues(context, attrs);
	}

	public SelectIcon(Context context) {
		super(context);
	}

	private void getValues(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectIcon);
		circleColor = ta.getResourceId(R.styleable.SelectIcon_circle_color, getResources().getColor(R.color.circle_color));
		tickColor = ta.getResourceId(R.styleable.SelectIcon_tick_color, getResources().getColor(R.color.tick_color));
		tickWidth = ta.getResourceId(R.styleable.SelectIcon_tick_width, 3);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画外圆
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(getResources().getColor(circleColor));
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);

		// 画勾
		paint.setColor(getResources().getColor(tickColor));
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(tickWidth);
		Path path = new Path();
		path.moveTo(getWidth() / 5, getHeight() / 2);
		path.lineTo(getWidth() / 5 * 2, getHeight() - getHeight() / 4);
		path.lineTo(getWidth() - getWidth() / 5, getHeight() / 4);
		canvas.drawPath(path, paint);
	}
}
