package com.tatait.tataweibo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.fragment.MyFragment;

/**
 * 主页面
 * 
 * @author WSXL
 * 
 */
public class HomeActivity extends FragmentActivity {
	// 抽屉菜单对象
	public static DrawerLayout drawerLayout;
	private MyFragment myFragment;
	private RelativeLayout left_menu_layout, right_menu_layout;
	private ActionBarDrawerToggle drawerbar;
	// "设置", "注销", "关于", "退出"
	CharSequence[] items = Constants.home_items;
	AlertDialog imageDialog;
	boolean isShown = false;
	private Oauth2AccessToken mAccessToken;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame_activity);
		initFragment();
		initEvent();

	}

	public void openLeftLayout() {
		if (drawerLayout.isDrawerOpen(left_menu_layout)) {
			drawerLayout.closeDrawer(left_menu_layout);
		} else {
			drawerLayout.openDrawer(left_menu_layout);

		}
	}

	// 消息开关事件
	public void openRightLayout() {
		if (drawerLayout.isDrawerOpen(right_menu_layout)) {
			drawerLayout.closeDrawer(right_menu_layout);
		} else {
			drawerLayout.openDrawer(right_menu_layout);
		}
	}

	private void initEvent() {
		drawerbar = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_launcher, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		drawerLayout.setDrawerListener(drawerbar);
	}

	public void initFragment() {
		myFragment = new MyFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction f_transaction = fragmentManager.beginTransaction();
		f_transaction.replace(R.id.main_content_frame_parent, myFragment);
		f_transaction.commitAllowingStateLoss();
		initLeftLayout();
		// initRightLayout();
	}

	public void initLeftLayout() {
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
		// 设置透明
		drawerLayout.setScrimColor(0x00000000);
		// 左边菜单
		left_menu_layout = (RelativeLayout) findViewById(R.id.main_left_drawer_layout);
		View view2 = getLayoutInflater().inflate(R.layout.home_left_menu, null);
		ImageView cover_user_photo = (ImageView) view2
				.findViewById(R.id.cover_user_photo);
		TextView home_left_menu_user = (TextView) view2
				.findViewById(R.id.home_left_menu_user);
		TextView home_left_menu_location = (TextView) view2
				.findViewById(R.id.home_left_menu_location);
		TextView home_left_menu_qianming = (TextView) view2
				.findViewById(R.id.home_left_menu_qianming);
		TextView userinfo_friendsCount = (TextView) view2
				.findViewById(R.id.userinfo_friendsCount);
		TextView userinfo_statusesCount = (TextView) view2
				.findViewById(R.id.userinfo_statusesCount);
		TextView userinfo_followingCount = (TextView) view2
				.findViewById(R.id.userinfo_followingCount);
		TextView userinfo_favouritesCount = (TextView) view2
				.findViewById(R.id.userinfo_favouritesCount);
		left_menu_layout.addView(view2);
		UserInfo user = UserSession.nowUser;
		cover_user_photo.setImageDrawable(user.getUser_head());
		home_left_menu_user.setText(user.getUser_name());
		home_left_menu_location.setText("loc:" + user.getLocation());
		home_left_menu_qianming.setText(user.getDescription());
		userinfo_friendsCount.setText("关注数：" + user.getFriends_count());
		userinfo_statusesCount.setText("微博数：" + user.getStatuses_count());
		userinfo_followingCount.setText("粉丝数：" + user.getFollowers_count());
		userinfo_favouritesCount.setText("收藏数：" + user.getFavourites_count());
	}

	public void initRightLayout() {
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
		// 设置透明
		drawerLayout.setScrimColor(0x00000000);
		// 左边菜单
		right_menu_layout = (RelativeLayout) findViewById(R.id.main_right_drawer_layout);
		View view2 = getLayoutInflater().inflate(R.layout.home_left_menu, null);
		ImageView cover_user_photo = (ImageView) view2
				.findViewById(R.id.cover_user_photo);
		TextView home_left_menu_user = (TextView) view2
				.findViewById(R.id.home_left_menu_user);
		TextView home_left_menu_location = (TextView) view2
				.findViewById(R.id.home_left_menu_location);
		TextView home_left_menu_qianming = (TextView) view2
				.findViewById(R.id.home_left_menu_qianming);
		right_menu_layout.addView(view2);
		UserInfo user = UserSession.nowUser;
		cover_user_photo.setImageDrawable(user.getUser_head());
		home_left_menu_user.setText(user.getUser_name());
		home_left_menu_location.setText(user.getLocation());
		home_left_menu_qianming.setText(user.getDescription());
	}

	/**
	 * 设置键盘事件处理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (imageDialog == null) {
				imageDialog = new AlertDialog.Builder(this).setTitle("更多选项")
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								if (item == 0) {
									Toast.makeText(HomeActivity.this,
											"功能待开发...", Toast.LENGTH_LONG)
											.show();
								} else if (item == 1) {
									logoff();
								} else if (item == 2) {
									Toast.makeText(HomeActivity.this,
											"功能待开发...", Toast.LENGTH_LONG)
											.show();
								}
							}
						}).create();
				imageDialog.show();
				isShown = true;
			} else {
				if (isShown) {
					imageDialog.hide();
					isShown = false;
				} else {
					imageDialog.show();
					isShown = true;
				}

			}
		}else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.quit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				stopSer();
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (imageDialog != null) {
			imageDialog.dismiss();
		}
	}

	public void goOAuth() {
		Intent intent = new Intent(this, OAuthActivity.class);
		startActivity(intent);
		finish();
	}

	public void logoff() {
		AccessTokenKeeper.clear(getApplicationContext());
		mAccessToken = new Oauth2AccessToken();
		Toast.makeText(HomeActivity.this, "已注销", Toast.LENGTH_LONG).show();
		goOAuth();
	}

	public void stopSer() {
		if (Tools.isServiceRunning(this,
				"com.tatait.tataweibo.service.MusicService")) {
			stopService(new Intent(HomeActivity.this, MusicService.class));
		}
	}

}