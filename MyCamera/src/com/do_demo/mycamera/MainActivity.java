package com.do_demo.mycamera;

import java.util.ArrayList;
import java.util.List;

import com.do_demo.mycamera.adapter.BgAdapter;
import com.do_demo.mycamera.adapter.FilterAdapter;
import com.do_demo.mycamera.adapter.StickersAdapter;
import com.do_demo.mycamera.bean.OptionBean;
import com.do_demo.mycamera.util.GPUImageFilterTools;
import com.do_demo.mycamera.util.GPUImageFilterTools.FilterAdjuster;
import com.do_demo.mycamera.util.AppVar;
import com.do_demo.mycamera.util.LomoFilter;
import com.do_demo.mycamera.util.PhotoFilter;
import com.do_demo.mycamera.util.PhotoLoad;
import com.do_demo.mycamera.util.ScreenShot;
import com.do_demo.mycamera.util.Share;
import com.do_demo.mycamera.util.StickersHelper;
import com.do_demo.mycamera.util.TouchEvent;
import com.do_demo.mycamera.widget.HorizontalListView;
import com.do_demo.mycamera.widget.TitleBar;
import com.do_demo.mycamera.widget.TitleBar.OnShareBtnClickListener;
import com.do_demo.mycamera.widget.TouchImageView;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context context = this;
	private TouchImageView touchImageView;// 从相册或者相机中取出的图片
	private Bitmap bitmap;// 从相册或者相机中取出的图片的Btimap实例
	private GPUImageFilter mFilter;// 滤镜实例
	private String path;// 图片文件

	private RelativeLayout img_lot;
	private RelativeLayout img_bg;
	private RelativeLayout img_stickers;
	private FrameLayout img_all;
	private int padding_left = 0;
	private int padding_top = 0;

	private LinearLayout btn_bg;
	private LinearLayout btn_stickers;
	private LinearLayout btn_filter;
	private ImageView btn_img_bg;
	private ImageView btn_img_filter;
	private ImageView btn_img_stickers;
	private TextView btn_txt_bg;
	private TextView btn_txt_filter;
	private TextView btn_txt_stickers;
	private TitleBar titleBar;

	// 横向ListView
	private HorizontalListView mOptionsListView;
	// 适配器
	private StickersAdapter stickersAdapter;
	private FilterAdapter filterAdapter;
	private BgAdapter bgAdapter;

	// 功能选项实体类
	private List<OptionBean> optionBeanList;

	public static int lastFilterPosition = 0;
	public static ImageView lastFilterSelect;

	public static int lastBgPosition = 0;
	public static ImageView lastBgSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_m);

		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * 获取图片
	 */
	private void getBitmap() {
		path = getIntent().getStringExtra("path");
		bitmap = PhotoLoad.getBitmap(this, path);
		touchImageView.setImageBitmap(bitmap);
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		PictureSelectActivity.loadingDialog.dismiss();
		touchImageView = (TouchImageView) findViewById(R.id.img_src);
		getBitmap();
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		btn_bg = (LinearLayout) findViewById(R.id.btn_bg);
		btn_stickers = (LinearLayout) findViewById(R.id.btn_stickers);
		btn_filter = (LinearLayout) findViewById(R.id.btn_filter);

		img_lot = (RelativeLayout) findViewById(R.id.img_lot);
		img_bg = (RelativeLayout) findViewById(R.id.img_bg);
		img_stickers = (RelativeLayout) findViewById(R.id.img_stickers);
		img_all = (FrameLayout) findViewById(R.id.img_all);

		mOptionsListView = (HorizontalListView) findViewById(R.id.options_horizontalListView);
		mOptionsListView = (HorizontalListView) findViewById(R.id.options_horizontalListView);

		optionBeanList = new ArrayList<OptionBean>();

		btn_img_bg = (ImageView) findViewById(R.id.btn_img_bg);
		btn_img_filter = (ImageView) findViewById(R.id.btn_img_filter);
		btn_img_stickers = (ImageView) findViewById(R.id.btn_img_stickers);
		btn_txt_bg = (TextView) findViewById(R.id.btn_txt_bg);
		btn_txt_filter = (TextView) findViewById(R.id.btn_txt_filter);
		btn_txt_stickers = (TextView) findViewById(R.id.btn_txt_stickers);

		// 初始化时,加载相框内容
		getBgState();

		// 设置事件
		initEvent();
	}

	/**
	 * 设置事件
	 */
	private void initEvent() {
		btn_bg.setOnClickListener(new OnClickListener() {

			// 点击相框
			@Override
			public void onClick(View v) {
				getBgState();
			}
		});

		// 点击小装饰品
		btn_stickers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setBtnState("btn_stickers");
				final int[] res = { R.drawable.sticker3, R.drawable.sticker1, R.drawable.sticker2 };
				optionBeanList.clear();
				for (int i = 0; i < res.length; i++) {
					OptionBean optionBean = new OptionBean();
					optionBean.setResId(res[i]);
					optionBeanList.add(optionBean);
				}

				stickersAdapter = new StickersAdapter(context, optionBeanList);
				mOptionsListView.setAdapter(stickersAdapter);

				mOptionsListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> ad, View v, int position, long id) {
						// System.out.println(position);
						StickersHelper.addStickers(MainActivity.this, img_stickers, res[position]);
					}
				});
			}
		});

		// 点击滤镜
		btn_filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setBtnState("btn_filter");

				final String[] filterName = { "", PhotoFilter.FILTER_006, PhotoFilter.FILTER_008, PhotoFilter.FILTER_010, PhotoFilter.FILTER_HISTORY, PhotoFilter.FILTER_BARANNAN, PhotoFilter.FILTER_C1, PhotoFilter.FILTER_C2, PhotoFilter.FILTER_C3, PhotoFilter.FILTER_GOTHAM, PhotoFilter.FILTER_HEFE, PhotoFilter.FILTER_LOMO, PhotoFilter.FILTER_LORD_KELVIN, PhotoFilter.FILTER_NASHVILLE, PhotoFilter.FILTER_C4, PhotoFilter.FILTER_X_PRO };
				final int[] res = { R.drawable.none, R.drawable.a006, R.drawable.a008, R.drawable.history, R.drawable.barannan, R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.gotham, R.drawable.hefe, R.drawable.lomo, R.drawable.lord_kelvin, R.drawable.nashville, R.drawable.c4, R.drawable.x_pro };
				optionBeanList.clear();
				for (int i = 0; i < res.length; i++) {
					OptionBean optionBean = new OptionBean();
					optionBean.setResId(res[i]);
					optionBeanList.add(optionBean);
				}
				filterAdapter = new FilterAdapter(context, optionBeanList);
				mOptionsListView.setAdapter(filterAdapter);
				mOptionsListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> ad, View v, int position, long id) {

						// System.out.println(position);

						// StickersHelper.addStickers(MainActivity.this,
						// img_stickers, res[position]);
						// bitmap=PhotoFilter.getBitMap(context,
						// bitmap,filterName[position] );

						if (position != lastFilterPosition) {
							optionBeanList.get(lastFilterPosition).setSelect(false);
							lastFilterSelect.setVisibility(View.INVISIBLE);

							// 点击时,显示打勾图片,表示已选择
							optionBeanList.get(position).setSelect(true);
							ImageView select_img = (ImageView) v.findViewById(R.id.select_img);
							select_img.setVisibility(View.VISIBLE);
							if (position == 0) {
								touchImageView.setImageBitmap(bitmap);
							} else {
								touchImageView.setImageBitmap(PhotoFilter.getBitMap(context, bitmap, filterName[position]));
							}

							lastFilterSelect = select_img;
							lastFilterPosition = position;
						}
					}
				});

			}
		});

		// 分享按钮点击事件
		titleBar.setOnShareBtnClickListener(new OnShareBtnClickListener() {

			@Override
			public void onShareBtnClick() {
				ImageView lastRemoveBtn = AppVar.getInstance().getLastRemoveBtn();
				if (lastRemoveBtn != null) {
					lastRemoveBtn.setVisibility(View.GONE);
				}
				String filePath = "/sdcard/share.jpg";
				ScreenShot.shoot(img_all, filePath, padding_left, padding_top);
				Share.shareMsg(context, "aaa", "bbb", "CCC", filePath);
			}
		});
	}

	private void getBgState() {
		setBtnState("btn_bg");
		final int[] res_small = { R.drawable.edit_drag_cancel, 
				R.drawable.frame1_small,R.drawable.frame2_small ,R.drawable.frame3_small,
				R.drawable.frame4_small,R.drawable.frame5_small,R.drawable.frame6_small,
				R.drawable.frame7_small,R.drawable.frame8_small};
		final int [] res = { R.drawable.edit_drag_cancel, 
				R.drawable.frame1,R.drawable.frame2 ,R.drawable.frame3,
				R.drawable.frame4,R.drawable.frame5,R.drawable.frame6,
				R.drawable.frame7,R.drawable.frame8};
		optionBeanList.clear();
		for (int i = 0; i < res_small.length; i++) {
			OptionBean optionBean = new OptionBean();
			optionBean.setResId(res_small[i]);
			optionBeanList.add(optionBean);
		}

		bgAdapter = new BgAdapter(context, optionBeanList);
		mOptionsListView.setAdapter(bgAdapter);

		mOptionsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> ad, View v, int position, long id) {

				// System.out.println(position);

				StickersHelper.addBackground(MainActivity.this, img_bg, res[position]);
				BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(res[position]);
				float igw = bitmapDrawable.getBitmap().getWidth();
				float igh = bitmapDrawable.getBitmap().getHeight();
				float Sw = img_all.getWidth() / igw;
				float Sh = img_all.getHeight() / igh;
				float sc = Math.min(Sw, Sh);

				/*
				 * System.out.println("sw:" + Sw + "-w:" + igw + "-s:" + (igw *
				 * sc)); System.out.println("sh:" + Sh + "-h:" + igh + "-s:" +
				 * (igh * sc));
				 */
				padding_left = position == 0 ? 0 : (int) (img_all.getWidth() - (igw * sc)) / 2;
				padding_top = position == 0 ? 0 : (int) (img_all.getHeight() - (igh * sc)) / 2;
				// System.out.println(lf+"--------"+tf);

				img_all.setPadding(padding_left, padding_top, padding_left, padding_top);

				if (position != lastBgPosition) {
					optionBeanList.get(lastBgPosition).setSelect(false);
					lastBgSelect.setVisibility(View.INVISIBLE);

					// 点击时,显示打勾图片,表示已选择
					optionBeanList.get(position).setSelect(true);
					ImageView select_img = (ImageView) v.findViewById(R.id.select_img);
					select_img.setVisibility(View.VISIBLE);

					lastBgSelect = select_img;
					lastBgPosition = position;
				}
			}
		});
	}

	/**
	 * 设置按钮的状态
	 */
	private void setBtnState(String btnName) {

		if (btnName.equals("btn_bg")) {
			System.out.println("btn_bg");
			btn_bg.setEnabled(false);
			btn_stickers.setEnabled(true);
			btn_filter.setEnabled(true);
			btn_img_bg.setImageResource(R.drawable.icon_frame_select);
			btn_txt_bg.setTextColor(getResources().getColor(R.color.blue));
			btn_img_filter.setImageResource(R.drawable.icon_filter_unselect);
			btn_txt_filter.setTextColor(Color.WHITE);
			btn_img_stickers.setImageResource(R.drawable.icon_stickers_unselect);
			btn_txt_stickers.setTextColor(Color.WHITE);
		} else if (btnName.equals("btn_stickers")) {
			System.out.println("btn_stickers");
			btn_bg.setEnabled(true);
			btn_stickers.setEnabled(false);
			btn_filter.setEnabled(true);
			btn_img_bg.setImageResource(R.drawable.icon_frame_unselect);
			btn_txt_bg.setTextColor(Color.WHITE);
			btn_img_filter.setImageResource(R.drawable.icon_filter_unselect);
			btn_txt_filter.setTextColor(Color.WHITE);
			btn_img_stickers.setImageResource(R.drawable.icon_stickers_select);
			btn_txt_stickers.setTextColor(getResources().getColor(R.color.blue));
		} else if (btnName.equals("btn_filter")) {
			System.out.println("btn_filter");
			btn_bg.setEnabled(true);
			btn_stickers.setEnabled(true);
			btn_filter.setEnabled(false);
			btn_img_bg.setImageResource(R.drawable.icon_frame_unselect);
			btn_txt_bg.setTextColor(Color.WHITE);
			btn_img_filter.setImageResource(R.drawable.icon_filter_select);
			btn_txt_filter.setTextColor(getResources().getColor(R.color.blue));
			btn_img_stickers.setImageResource(R.drawable.icon_stickers_unselect);
			btn_txt_stickers.setTextColor(Color.WHITE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		lastFilterPosition = 0;
		lastFilterSelect = null;

		lastBgPosition = 0;
		lastBgSelect = null;
	}
}
