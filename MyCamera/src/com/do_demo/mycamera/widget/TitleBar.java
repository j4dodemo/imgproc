package com.do_demo.mycamera.widget;

import com.do_demo.mycamera.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends LinearLayout {
	private OnShareBtnClickListener listener;

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TitleBar(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View view = View.inflate(context, R.layout.layout_title_bar, null);
		TextView shareBtn = (TextView) view.findViewById(R.id.share_btn);
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				listener.onShareBtnClick();
			}
		});
		addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public interface OnShareBtnClickListener {
		public void onShareBtnClick();
	}

	public void setOnShareBtnClickListener(OnShareBtnClickListener listener) {
		this.listener = listener;
	}
}
