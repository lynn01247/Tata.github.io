package com.tatait.tataweibo.drawLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.AccessTokenKeeper;
import com.tatait.tataweibo.Constants;
import com.tatait.tataweibo.HomeActivity;
import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.XListView.XListView;
import com.tatait.tataweibo.XListView.XListView.IXListViewListener;
import com.tatait.tataweibo.adapter.HomeAdapters;
import com.tatait.tataweibo.pojo.ContentInfo;
import com.tatait.tataweibo.pojo.UserInfo;
import com.tatait.tataweibo.util.DateUtils;
import com.tatait.tataweibo.util.FileService;
import com.tatait.tataweibo.util.Tools;

public class MyFragment extends Fragment implements IXListViewListener {
	private View home_view;
	Date nowDate, startDate;
	private LinearLayout load_progress = null;
	// 保存需要显示的多条微博数据
	public LinkedList<ContentInfo> contentList = null;
	private Button refresh_weibo, writer_weibo, menu_btn;
	private TextView login_user;
	private UserInfo user;
	// 微博认证
	private Oauth2AccessToken accessToken = null;
	private HomeAdapters adapater = null;
	private Tools tools;
	// 下拉刷新
	private XListView home_lv;
	private Handler mHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		home_view = inflater.inflate(R.layout.home, null);
		initView();
		return home_view;
	}

	public void initView() {
		load_progress = (LinearLayout) home_view
				.findViewById(R.id.load_progress);
		home_lv = (XListView) home_view.findViewById(R.id.home_lv);
		home_lv.setPullLoadEnable(true);
		refresh_weibo = (Button) home_view.findViewById(R.id.btn_refresh);
		menu_btn = (Button) home_view.findViewById(R.id.menu_btn);
		login_user = (TextView) home_view.findViewById(R.id.txt_wb_title);
		writer_weibo = (Button) home_view.findViewById(R.id.btn_writer);

		MyClick click = new MyClick();
		refresh_weibo.setOnClickListener(click);
		writer_weibo.setOnClickListener(click);
		menu_btn.setOnClickListener(click);

		user = UserSession.nowUser;
		login_user.setText(user.getUser_name());
		mHandler = new Handler();
		startDate = Calendar.getInstance().getTime();
		home_lv.setXListViewListener(this);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LoadHomeData();
			}

		}, 400);
	}

	// 按钮事件
	class MyClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_refresh:
				FileService file = new FileService((HomeActivity) getActivity());
				file.clearData();
				LoadHomeData();
				Toast.makeText((HomeActivity) getActivity(),
						R.string.dateFlash, Toast.LENGTH_LONG).show();
				break;
			case R.id.menu_btn:
				((HomeActivity) getActivity()).openLeftLayout();
				break;
			}
		}
	}

	// IXListViewListener 需实现的方法
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ReflashHomeData();
				onLoad();
			}
		}, 1000);
	}

	// IXListViewListener 需实现的方法
	@Override
	public void onLoadMore() {
		LoadMoreHomeData();
		onLoad();
	}

	// 重写刷新时间
	private void onLoad() {
		home_lv.stopRefresh();
		home_lv.stopLoadMore();
		nowDate = Calendar.getInstance().getTime();
		String time = new DateUtils().twoDateDistance(startDate, nowDate);
		home_lv.setRefreshTime(time);
		startDate = nowDate;
	}

	// 加载数据
	public void LoadHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper
				.readAccessToken(((HomeActivity) getActivity()));
		contentList = tools.loadHomeData(Constants.GET_PUBLIC, accessToken,
				((HomeActivity) getActivity()));
		//
		if (contentList != null) {
			// 创建一个Adapter设置ListView中的每项Item项数据
			adapater = new HomeAdapters(((HomeActivity) getActivity()),
					contentList);
			// 设置listview上的item点击事件处理
			home_lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Object obj = view.getTag();
					if (obj != null) {
						// 获得一条weibo数据的id（唯一标识）
						String weiboId = obj.toString();
						// 跳转到一条具体的微博显示页面
						// Intent intent = new
						// Intent(HomeActivity.this,ContentActivity.class);
						Bundle bundle = new Bundle();
						// 参数的设置
						bundle.putString("weiboId", weiboId);
						// 参数绑定
						// intent.putExtras(bundle);
						// startActivity(intent);
					}
				}
			});
			// 将adapter和listview关联
			home_lv.setAdapter(adapater);
		}
		// 隐藏进度条
		load_progress.setVisibility(View.GONE);
		home_lv.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载更多
	 */
	public void LoadMoreHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper
				.readAccessToken((HomeActivity) getActivity());
		contentList = tools.loadHomeData(Constants.GET_PUBLIC_LOADMORE,
				accessToken, (HomeActivity) getActivity());
		adapater.notifyDataSetChanged();
	}

	/**
	 * 刷新首页
	 */
	public void ReflashHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper
				.readAccessToken((HomeActivity) getActivity());
		contentList = tools.loadHomeData(Constants.GET_PUBLIC_REFLASH,
				accessToken, (HomeActivity) getActivity());
		adapater.notifyDataSetChanged();
	}
}
