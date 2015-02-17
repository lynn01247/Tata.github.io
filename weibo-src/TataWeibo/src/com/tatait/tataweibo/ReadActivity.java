package com.tatait.tataweibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.file.ViewFile;
import com.tatait.tataweibo.util.show.CircularImage;

/**
 * 信息页面
 * 
 * @author WSXL
 * 
 */
public class ReadActivity extends Activity {
	private long exitTime = 0;
	private boolean isRead = false;
	private ImageView circularImage, home_title_bar_image;
	private Button menu_btn_right, btnOpen, btnDemo, btnSetting,read_btn_close;
	private RelativeLayout read_relative, read_scroll_relative;
	public TextView txt_wb_title, read_view_contents;
	// 启动Activity的返回码
	final static int REQUST_CODE_OPEN_FILE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading);
		txt_wb_title = (TextView) findViewById(R.id.home_title_login_user);
		read_view_contents = (TextView) findViewById(R.id.read_view_contents);
		home_title_bar_image = (ImageView) findViewById(R.id.home_title_bar_image);
		read_relative = (RelativeLayout) findViewById(R.id.read_relative);
		read_scroll_relative = (RelativeLayout) findViewById(R.id.read_scroll_relative);
		circularImage = (CircularImage) findViewById(R.id.home_title_bar_user_photo);
		menu_btn_right = (Button) findViewById(R.id.menu_btn_right);
		txt_wb_title.setText(R.string.love_read);
		home_title_bar_image.setVisibility(View.GONE);
		circularImage.setVisibility(View.GONE);
		menu_btn_right.setVisibility(View.GONE);

		btnOpen = (Button) this.findViewById(R.id.read_btn_open);
		btnDemo = (Button) this.findViewById(R.id.read_btn_demo);
		read_btn_close = (Button) this.findViewById(R.id.read_btn_close);
		btnSetting = (Button) this.findViewById(R.id.read_btn_setting);
		// 实现点击事件的监听接口
		OnClickListener oclClick = new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				// 打开文件
				case R.id.read_btn_open: {
					Intent i = new Intent(ReadActivity.this,
							OpenFileActivity.class);
					startActivityForResult(i, REQUST_CODE_OPEN_FILE);
					break;
				}
				case R.id.read_btn_demo: {
					try {
						StringBuffer sBuffer = new StringBuffer();
						if (!isRead) {
							InputStream fInputStream = ReadActivity.this
									.getAssets().open("instruction.txt");
							InputStreamReader inputStreamReader = new InputStreamReader(
									fInputStream, "UTF-8");
							BufferedReader in = new BufferedReader(
									inputStreamReader);
							while (in.ready()) {
								sBuffer.append(in.readLine() + "\n");
							}
							isRead = true;
							in.close();
							read_view_contents.setText(sBuffer);
						}
						read_relative.setVisibility(View.INVISIBLE);
						read_scroll_relative.setVisibility(View.VISIBLE);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case R.id.read_btn_close: {
					read_relative.setVisibility(View.VISIBLE);
					read_scroll_relative.setVisibility(View.INVISIBLE);
					break;
				}
				// 用户设置
				// case R.id.main_btn_setting: {
				// Intent i = new Intent(ReadActivity.this,
				// SettingActivity.class);
				// startActivity(i);
				// break;
				// }
				}
			}
		};
		// 事件监听器和按钮绑定
		btnOpen.setOnClickListener(oclClick);
		btnDemo.setOnClickListener(oclClick);
		read_btn_close.setOnClickListener(oclClick);
		btnSetting.setOnClickListener(oclClick);
	}

	// 处理Activity返回的结果
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (requestCode == REQUST_CODE_OPEN_FILE) {
				// 用户打开文件，返回一个完整的文件路径
				Intent i = new Intent(this, ViewFile.class);
				Bundle b = data.getExtras();
				i.putExtras(b);
				startActivity(i);
			}
			break;
		}
	}

	// 主菜单点击返回键，弹出对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.quit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				stopService(new Intent(ReadActivity.this, MusicService.class));
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
