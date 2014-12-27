package com.tatait.tataweibo;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private TextView text;
	private ActionBarDrawerToggle drawerbar;

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
		View view2 = getLayoutInflater().inflate(R.layout.menu_layout, null);
		text = (TextView) view2.findViewById(R.id.text);
		text.setText("左边测试菜单");
		left_menu_layout.addView(view2);
	}
}