package com.tatait.tataweibo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.show.SlidingMenu;

public class TabMainActivity extends TabActivity {
	public View msgTitle;// 信息头部按钮
	CharSequence[] items = Constants.beautiful_items;
	private SlidingMenu mMenu;
	private RadioGroup mainGroup;
	private TabHost th;
	public static ImageButton menu_play_button, menu_next_button;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 各子页的加载
		this.setContentView(R.layout.maintabs);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		menu_play_button = (ImageButton) findViewById(R.id.menu_play_button);
		menu_next_button = (ImageButton) findViewById(R.id.menu_next_button);
		mainGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		menu_play_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (MusicPlayActivity.mAdapter != null) {
					Intent play = new Intent(TabMainActivity.this,
							MusicService.class);
					play.putExtra("control", "play");
					startService(play);
				} else {
					mainGroup.check(R.id.radio_button2);
					th.setCurrentTabByTag("TAB_MUSIC");
					Toast.makeText(TabMainActivity.this, R.string.music_init, Toast.LENGTH_SHORT).show();
					Intent play = new Intent(TabMainActivity.this,
							MusicService.class);
					play.putExtra("control", "play");
					startService(play);
				}

			}
		});
		menu_next_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (MusicPlayActivity.mAdapter != null) {
					Intent next = new Intent(TabMainActivity.this,
							MusicService.class);
					next.putExtra("control", "next");
					startService(next);
				} else {
					mainGroup.check(R.id.radio_button2);
					th.setCurrentTabByTag("TAB_MUSIC");
					Toast.makeText(TabMainActivity.this, R.string.music_init, Toast.LENGTH_SHORT).show();
					Intent next = new Intent(TabMainActivity.this,
							MusicService.class);
					next.putExtra("control", "next");
					startService(next);
				}
			}
		});
		// 完成各子页集成
		th = this.getTabHost();
		th.addTab(th.newTabSpec("TAB_HOME").setIndicator("TAB_HOME")
				.setContent(new Intent(this, HomeActivity.class)));
		// th.addTab(th.newTabSpec("TAB_MSG").setIndicator("TAB_MSG")
		// .setContent(new Intent(this, ReadActivity.class)));
		th.addTab(th.newTabSpec("TAB_MUSIC").setIndicator("TAB_MUSIC")
				.setContent(new Intent(this, MusicPlayActivity.class)));

		th.addTab(th.newTabSpec("TAB_LOVE_READ").setIndicator("TAB_LOVE_READ")
				.setContent(new Intent(this, ReadActivity.class)));

		th.addTab(th.newTabSpec("TAB_MORE").setIndicator("TAB_MORE")
				.setContent(new Intent(this, MoreActivity.class)));
		mainGroup.check(R.id.radio_button0);

		mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int rid) {
				switch (rid) {
				case R.id.radio_button0:// 首页
					th.setCurrentTabByTag("TAB_HOME");
					break;
				// case R.id.radio_button1:// 信息
				// th.setCurrentTabByTag("TAB_MSG");
				// msgTitle.setVisibility(View.VISIBLE);
				// break;
				case R.id.radio_button2:// 音乐
					th.setCurrentTabByTag("TAB_MUSIC");
					break;

				case R.id.radio_button3:// 阅读
					th.setCurrentTabByTag("TAB_LOVE_READ");
					break;

				case R.id.radio_button4:// 更多
					th.setCurrentTabByTag("TAB_MORE");
				}
			}

		});
	}

	public void toggleMenu(View view) {
		mMenu.toggle();
	}
}
