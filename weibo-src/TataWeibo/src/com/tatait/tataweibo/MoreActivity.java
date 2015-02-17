package com.tatait.tataweibo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.MySlipSwitch.OnSwitchListener;
import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.show.CircularImage;
import com.tatait.tataweibo.util.show.SlidingMenu;

public class MoreActivity extends Activity {
	// 使用GridView定义的自定义菜单
	private GridView gv_foot_menu;
	private SlidingMenu mMenu;
	CharSequence[] items = Constants.beautiful_items;
	public TextView txt_wb_title;
	private CircularImage circularImage;
	private ImageView home_title_bar_image;
	private Button menu_btn_right;
	private long exitTime = 0;
	private MySlipSwitch slipswitch_MSL;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		txt_wb_title = (TextView) findViewById(R.id.home_title_login_user);
		home_title_bar_image = (ImageView) findViewById(R.id.home_title_bar_image);
		circularImage = (CircularImage) findViewById(R.id.home_title_bar_user_photo);
		menu_btn_right = (Button) findViewById(R.id.menu_btn_right);
		txt_wb_title.setText(R.string.setting_text);
		home_title_bar_image.setVisibility(View.GONE);
		circularImage.setVisibility(View.GONE);
		menu_btn_right.setVisibility(View.GONE);

		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		slipswitch_MSL = (MySlipSwitch)findViewById(R.id.main_myslipswitch);
        slipswitch_MSL.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
        slipswitch_MSL.setSwitchState(AccessTokenKeeper.readLoad(MoreActivity.this));
        slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {
			
			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if(isSwitchOn) {
					AccessTokenKeeper.writeLoad(MoreActivity.this, true);
				} else {
					AccessTokenKeeper.writeLoad(MoreActivity.this, false);
				}
			}
		});
	}

	/**
	 * 设置键盘事件处理
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (gv_foot_menu == null) {
				loadButtomMenu();
			}
			if (gv_foot_menu.getVisibility() == View.GONE) {
				gv_foot_menu.setVisibility(View.VISIBLE);
			} else {
				gv_foot_menu.setVisibility(View.GONE);
			}
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.quit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				if (Tools.isServiceRunning(this,
						"com.tatait.tataweibo.service.MusicService")) {
					stopService(new Intent(MoreActivity.this,
							MusicService.class));
				}
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}

	private void loadButtomMenu() {
		// 获得main.xml中的GridView组件
		gv_foot_menu = (GridView) this.findViewById(R.id.gv_foot_menu);
		// 设置组件背景
		gv_foot_menu.setBackgroundResource(android.R.drawable.bottom_bar);

		gv_foot_menu.setNumColumns(6);// 总共显示多少项
		gv_foot_menu.setGravity(Gravity.CENTER);// 中父组件的中间显示
		gv_foot_menu.setVerticalSpacing(5);// 垂直间隔
		gv_foot_menu.setHorizontalSpacing(5);// 水平间隔

		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();

		// 添加自定义菜单显示的图片
		map.put("itemImage", Integer.valueOf(R.drawable.usrinfo_fans_n)
				.toString()); //
		// 添加自定义菜单显示的文字
		map.put("itemText",
				this.getResources().getString(R.string.menu_friends_timeline));
		data.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage", Integer.valueOf(R.drawable.officialweibo)
				.toString());
		map.put("itemText",
				this.getResources().getString(R.string.menu_user_timeline));
		data.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage", Integer.valueOf(R.drawable.thumbs_up2).toString());
		map.put("itemText",
				this.getResources().getString(R.string.menu_comments_by_me));
		data.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage", Integer.valueOf(R.drawable.pl).toString());
		map.put("itemText",
				this.getResources().getString(R.string.menu_comments_to_me));
		data.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage", Integer.valueOf(R.drawable.menu_contact)
				.toString());
		map.put("itemText",
				this.getResources().getString(R.string.menu_userinfo_show));
		data.add(map);
		map = new HashMap<String, String>();

		map.put("itemImage", Integer.valueOf(R.drawable.menu_exit).toString());
		map.put("itemText",
				this.getResources().getString(R.string.menu_end_session));
		data.add(map);
		// 使用simpleAdapter进行数据的显示设置
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.foot_item_menu,
				new String[] { "itemImage", "itemText" }, new int[] {
						R.id.item_image, R.id.item_text });
		// 将adapter设置到GridView组件中
		gv_foot_menu.setAdapter(adapter);
		// 设置GridView事件
		gv_foot_menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:// 获得我关注的数据
					Toast.makeText(
							MoreActivity.this,
							items[(int) (Math.random() * items.length)]
									.toString(), Toast.LENGTH_LONG).show();
					break;
				case 1:
					Toast.makeText(
							MoreActivity.this,
							items[(int) (Math.random() * items.length)]
									.toString(), Toast.LENGTH_LONG).show();
					break;
				case 2:
					Toast.makeText(
							MoreActivity.this,
							items[(int) (Math.random() * items.length)]
									.toString(), Toast.LENGTH_LONG).show();
					break;
				case 3:
					Toast.makeText(
							MoreActivity.this,
							items[(int) (Math.random() * items.length)]
									.toString(), Toast.LENGTH_LONG).show();
					break;
				case 4:
					Toast.makeText(
							MoreActivity.this,
							items[(int) (Math.random() * items.length)]
									.toString(), Toast.LENGTH_LONG).show();
					break;
				case 5:
					gv_foot_menu.setVisibility(View.GONE);
				}
			}
		});
	}
}
