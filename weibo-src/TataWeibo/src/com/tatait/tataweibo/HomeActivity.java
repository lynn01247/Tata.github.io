package com.tatait.tataweibo;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.drawLayout.MyFragment;

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
	private RelativeLayout left_menu_layout;
	private ActionBarDrawerToggle drawerbar;
	// "设置", "注销", "关于", "退出"
	CharSequence[] items = Constants.home_items;
	AlertDialog imageDialog;
	boolean isShown = false;

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
		left_menu_layout.addView(view2);
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

								} else if (item == 1) {

								} else if (item == 2) {

								}
							}
						}).create();
				imageDialog.show();
				isShown = true;
			} else {
				if(isShown) {
					imageDialog.hide();
					isShown = false;
				}else{
					imageDialog.show();
					isShown = true;
				}
				
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageDialog.dismiss();
	}
	
}