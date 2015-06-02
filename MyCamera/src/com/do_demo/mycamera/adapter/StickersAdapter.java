package com.do_demo.mycamera.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.do_demo.mycamera.MainActivity;
import com.do_demo.mycamera.R;
import com.do_demo.mycamera.bean.OptionBean;
import com.do_demo.mycamera.widget.util.ViewHolder;

public class StickersAdapter extends CommonAdapter<OptionBean> {
	private List<OptionBean> data;
	
	public StickersAdapter(Context context, List<OptionBean> data) {
		super(context, data, R.layout.options_item);
		this.data = data;
	}

	@Override
	public void convert(ViewHolder holder, OptionBean optionBean) {
		Log.i("res", "optionBean.getResId()" + optionBean.getResId());
		((ImageView) holder.getView(R.id.optionIcon_imageView)).setImageResource(optionBean.getResId());
	}
}
