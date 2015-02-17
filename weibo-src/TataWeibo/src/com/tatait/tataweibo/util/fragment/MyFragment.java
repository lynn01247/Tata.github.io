package com.tatait.tataweibo.util.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.HomeActivity;
import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.MusicPlayActivity;
import com.tatait.tataweibo.R;
import com.tatait.tataweibo.WriterWeiboActivity;
import com.tatait.tataweibo.adapter.HomeAdapters;
import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.ContentInfo;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.DateUtils;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.file.FileService;
import com.tatait.tataweibo.util.show.XListView;
import com.tatait.tataweibo.util.show.XListView.IXListViewListener;

public class MyFragment extends Fragment implements IXListViewListener {
	private View home_view, vPopupWindow;
	private ListView popuplist;
	Date nowDate, startDate;
	private LinearLayout load_progress = null;
	// 保存需要显示的多条微博数据
	public LinkedList<ContentInfo> contentList = null;
	public ArrayList<HashMap<String, String>> homeSetMenuList = new ArrayList<HashMap<String, String>>();
	private Button menu_btn_right;
	private TextView login_user;
	private ImageView home_title_bar_image;
	private UserInfo user;
	// 微博认证
	private Oauth2AccessToken accessToken = null;
	private HomeAdapters adapater = null;
	private Tools tools;
	// 下拉刷新
	private XListView home_lv;
	private Handler mHandler;

	public PopupWindow pw = null;
	private int screenWidth, screenHeight; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		home_view = inflater.inflate(R.layout.home, null);
		vPopupWindow = inflater.inflate(R.layout.home_set_menu, null);
		findBy();
		initValue();
		initView();
		return home_view;
	}
	//获取资源ID
	private void findBy() {
		load_progress = (LinearLayout) home_view
				.findViewById(R.id.load_progress);
		home_lv = (XListView) home_view.findViewById(R.id.home_lv);
		home_lv.setPullLoadEnable(true);
		menu_btn_right = (Button) home_view.findViewById(R.id.menu_btn_right);
		login_user = (TextView) home_view
				.findViewById(R.id.home_title_login_user);
		home_title_bar_image = (ImageView) home_view
				.findViewById(R.id.home_title_bar_user_photo);
		popuplist = (ListView) vPopupWindow.findViewById(R.id.popuplist);
	}
	//初始化值
	private void initValue() {
		user = UserSession.nowUser;
		mHandler = new Handler();
		startDate = Calendar.getInstance().getTime();
		screenWidth = ((HomeActivity) getActivity()).getWindowManager()
				.getDefaultDisplay().getWidth();
		screenHeight = ((HomeActivity) getActivity()).getWindowManager()
				.getDefaultDisplay().getHeight();
		pw = new PopupWindow(vPopupWindow, screenWidth/2, screenHeight/3, true);
		//设置弹出框的List
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("itemTitle", "刷新");
		homeSetMenuList.add(map);
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("itemTitle", "发表微博");
		homeSetMenuList.add(map1);
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("itemTitle", "注销");
		homeSetMenuList.add(map2);
		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("itemTitle", "退出");
		homeSetMenuList.add(map4);
		HashMap<String, String> map5 = new HashMap<String, String>();
		map5.put("itemTitle", "返回");
		homeSetMenuList.add(map5);
	}

	public void initView() {
		//设置监听事件
		MyClick click = new MyClick();
		menu_btn_right.setOnClickListener(click);
		home_title_bar_image.setOnClickListener(click);
		//初始化标题栏：头像和登陆人
		login_user.setText(user.getUser_name());
		home_title_bar_image.setImageDrawable(user.getUser_head());
		//设置下拉刷新
		home_lv.setXListViewListener(this);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LoadHomeData();
			}
		}, 400);
		// 为弹出框设定自定义的布局
		pw.setContentView(vPopupWindow); 
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		ColorDrawable cd = new ColorDrawable(-0000); 
		pw.setBackgroundDrawable(cd);
		// 生成SimpleAdapter适配器对象
		SimpleAdapter mySimpleAdapter = new SimpleAdapter(
				(HomeActivity) getActivity(), homeSetMenuList,
				R.layout.home_set_menu_list,
				new String[] { "itemTitle" },
				new int[] { R.id.itemTitle });
		popuplist.setAdapter(mySimpleAdapter);
		// 添加点击事件
		popuplist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获得选中项的HashMap对象
				HashMap<String, String> map = (HashMap<String, String>) popuplist
						.getItemAtPosition(arg2);
				String title = map.get("itemTitle");
				if ("刷新".equals(title)) {
					pw.dismiss();
					FileService file = new FileService(
							(HomeActivity) getActivity());
					file.clearData();
					LoadHomeData();
					Toast.makeText((HomeActivity) getActivity(),
							R.string.dateFlash, Toast.LENGTH_LONG).show();
				} else if ("发表微博".equals(title)) {
					pw.dismiss();
					startActivity(new Intent((HomeActivity) getActivity(),
							WriterWeiboActivity.class));
//					getActivity().finish();
				}else if ("退出".equals(title)) {
					pw.dismiss();
					getActivity().finish();
					((HomeActivity) getActivity()).stopSer();
					System.exit(0);
				}else if ("注销".equals(title)) {
					pw.dismiss();
					((HomeActivity) getActivity()).logoff();
				}else if ("返回".equals(title)) {
					pw.dismiss();
				}
			}

		});
	}

	// 按钮事件
	class MyClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_btn_right:
				// 创建PopupWindow实例
				makePopupWindow();
				break;
			case R.id.home_title_bar_user_photo:
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

	/**
	 * 加载数据
	 */
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
						Toast.makeText((HomeActivity) getActivity(),
								"选择了ID为：" + weiboId + "的记录。", Toast.LENGTH_LONG)
								.show();
						// 跳转到一条具体的微博显示页面
						// Intent intent = new
						// Intent(HomeActivity.this,ContentActivity.class);
						// Bundle bundle = new Bundle();
						// 参数的设置
						// bundle.putString("weiboId", weiboId);
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

	public void makePopupWindow() {
		if(pw.isShowing()){
			pw.dismiss();
		}else{
			int dialgoWidth = pw.getWidth();
			pw.showAsDropDown(menu_btn_right, (screenWidth - dialgoWidth)-5, 2);
		}
		
	}
	public PopupWindow getPopupWindow(){
		return pw;
	}
	public void setPwShow() {
		if(pw!=null){
			int dialgoWidth = pw.getWidth();
			pw.showAsDropDown(menu_btn_right, (screenWidth - dialgoWidth)-5, 2);
		}
	}
}
