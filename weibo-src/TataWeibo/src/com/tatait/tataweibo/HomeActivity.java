package com.tatait.tataweibo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.XListView.XListView;
import com.tatait.tataweibo.XListView.XListView.IXListViewListener;
import com.tatait.tataweibo.adapter.HomeAdapters;
import com.tatait.tataweibo.pojo.ContentInfo;
import com.tatait.tataweibo.pojo.UserInfo;
import com.tatait.tataweibo.util.DateUtils;
import com.tatait.tataweibo.util.FileService;
import com.tatait.tataweibo.util.Tools;

/**
 * 主页面
 * 
 * @author WSXL
 * 
 */
public class HomeActivity extends Activity implements IXListViewListener {
	private static final String TAG = "HomeActivity";
	Date nowDate, startDate;
	private LinearLayout load_progress = null;
	// 保存需要显示的多条微博数据
	public LinkedList<ContentInfo> contentList = null;
	private Button refresh_weibo, writer_weibo;
	private TextView login_user;
	private UserInfo user;
	private Oauth2AccessToken accessToken = null;
	private HomeAdapters adapater = null;
	private Tools tools;
	private XListView home_lv;
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		Log.i(TAG, "onCreate");
		load_progress = (LinearLayout) findViewById(R.id.load_progress);

		home_lv = (XListView) findViewById(R.id.home_lv);
		home_lv.setPullLoadEnable(true);
		refresh_weibo = (Button) findViewById(R.id.btn_refresh);
		login_user = (TextView) findViewById(R.id.txt_wb_title);
		writer_weibo = (Button) findViewById(R.id.btn_writer);
		MyClick click = new MyClick();
		refresh_weibo.setOnClickListener(click);
		writer_weibo.setOnClickListener(click);
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

		}, 300);
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ReflashHomeData();
				Toast.makeText(HomeActivity.this, R.string.dateFlash,
						Toast.LENGTH_LONG).show();
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		LoadMoreHomeData();
		onLoad();
	}

	private void onLoad() {
		home_lv.stopRefresh();
		home_lv.stopLoadMore();
		nowDate = Calendar.getInstance().getTime();
		String time = new DateUtils().twoDateDistance(startDate, nowDate);
		home_lv.setRefreshTime(time);
		startDate = nowDate;
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "onStart");
		// init();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	class MyClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_refresh:
				FileService file = new FileService(HomeActivity.this);
				file.clearData();
				LoadHomeData();
				Toast.makeText(HomeActivity.this, R.string.dateFlash,
						Toast.LENGTH_LONG).show();
				break;
			case R.id.btn_writer:
				// Intent intent = new Intent(HomeActivity.this,
				// WriterWeiboActivity.class);
				// startActivity(intent);
				break;
			}
		}
	}

	public void LoadHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper.readAccessToken(this);
		contentList = tools.loadHomeData(Constants.GET_PUBLIC, accessToken,
				HomeActivity.this);
		//
		if (contentList != null) {
			// 创建一个Adapter设置ListView中的每项Item项数据
			adapater = new HomeAdapters(this, contentList);
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
	
	public void LoadMoreHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper.readAccessToken(this);
		contentList = tools.loadMoreHomeData(Constants.GET_PUBLIC, accessToken,
				HomeActivity.this);
		adapater.notifyDataSetChanged();
	}
	public void ReflashHomeData() {
		tools = Tools.getInstance();
		// 获得主要显示的数据
		accessToken = AccessTokenKeeper.readAccessToken(this);
		contentList = tools.reflashHomeData(Constants.GET_PUBLIC, accessToken,
				HomeActivity.this);
		adapater.notifyDataSetChanged();
	}
}
