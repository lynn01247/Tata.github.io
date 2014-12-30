package com.tatait.tataweibo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MoreActivity extends Activity {
	// 使用GridView定义的自定义菜单
	private GridView gv_foot_menu;
	CharSequence[] items = Constants.beautiful_items;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
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
			}
			return super.onKeyDown(keyCode, event);
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
						Toast.makeText(MoreActivity.this, items[(int)(Math.random() * items.length)].toString(), Toast.LENGTH_LONG)
								.show();
						break;
					case 1:
						Toast.makeText(MoreActivity.this, items[(int)(Math.random() * items.length)].toString(), Toast.LENGTH_LONG)
								.show();
						break;
					case 2:
						Toast.makeText(MoreActivity.this, items[(int)(Math.random() * items.length)].toString(), Toast.LENGTH_LONG)
								.show();
						break;
					case 3:
						Toast.makeText(MoreActivity.this, items[(int)(Math.random() * items.length)].toString(), Toast.LENGTH_LONG)
								.show();
						break;
					case 4:
						Toast.makeText(MoreActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG)
								.show();
						break;
					case 5:
						gv_foot_menu.setVisibility(View.GONE);
					}
				}
			});
		}
}
