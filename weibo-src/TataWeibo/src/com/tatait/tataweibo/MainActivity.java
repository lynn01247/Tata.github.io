package com.tatait.tataweibo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	public View msgTitle;// 信息头部按钮
	CharSequence[] items = Constants.beautiful_items;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 各子页的加载
		this.setContentView(R.layout.maintabs);
		msgTitle = this.findViewById(R.id.msg_title);
		// 对消息的处理

		// 完成各子页集成
		final TabHost th = this.getTabHost();
		th.addTab(th.newTabSpec("TAB_HOME").setIndicator("TAB_HOME")
				.setContent(new Intent(this, HomeActivity.class)));
		th.addTab(th.newTabSpec("TAB_MSG").setIndicator("TAB_MSG")
				.setContent(new Intent(this, MsgActivity.class)));
		th.addTab(th.newTabSpec("TAB_USER_INFO").setIndicator("TAB_USER_INFO")
				.setContent(new Intent(this, UserInfoActivity.class)));

		th.addTab(th.newTabSpec("TAB_SEARCH").setIndicator("TAB_SEARCH")
				.setContent(new Intent(this, MusicPlay.class)));

		th.addTab(th.newTabSpec("TAB_MORE").setIndicator("TAB_MORE")
				.setContent(new Intent(this, MoreActivity.class)));
		RadioGroup mainGroup = (RadioGroup) this.findViewById(R.id.main_radio);

		mainGroup.check(R.id.radio_button0);

		mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int rid) {
				// TODO Auto-generated method stub
				Log.d("radiou group", "you selected=" + rid);
				switch (rid) {
				case R.id.radio_button0:// 首页
					th.setCurrentTabByTag("TAB_HOME");
					msgTitle.setVisibility(View.GONE);
					break;
				case R.id.radio_button1:// 信息
					th.setCurrentTabByTag("TAB_MSG");
					msgTitle.setVisibility(View.VISIBLE);
					break;
				case R.id.radio_button2:// 资料
					th.setCurrentTabByTag("TAB_USER_INFO");
					msgTitle.setVisibility(View.GONE);
					break;

				case R.id.radio_button3:// 搜索
					th.setCurrentTabByTag("TAB_SEARCH");
					msgTitle.setVisibility(View.GONE);
					break;

				case R.id.radio_button4:// 更多
					msgTitle.setVisibility(View.GONE);
					th.setCurrentTabByTag("TAB_MORE");
				}
			}

		});

		Button left = (Button) findViewById(R.id.bt_group_left);
		Button middle = (Button) findViewById(R.id.bt_group_middle);
		Button right = (Button) findViewById(R.id.bt_group_right);

		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(
						MainActivity.this,
						"@我之名言："
								+ items[(int) (Math.random() * items.length)]
										.toString(), Toast.LENGTH_LONG).show();
			}
		});
		middle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(
						MainActivity.this,
						"评论之名言："
								+ items[(int) (Math.random() * items.length)]
										.toString(), Toast.LENGTH_LONG).show();
			}
		});
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(
						MainActivity.this,
						"私信之名言："
								+ items[(int) (Math.random() * items.length)]
										.toString(), Toast.LENGTH_LONG).show();
			}
		});
	}
}
