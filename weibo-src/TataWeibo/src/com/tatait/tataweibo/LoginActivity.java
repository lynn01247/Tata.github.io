package com.tatait.tataweibo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.LoadActivity.UserSession;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.pojo.UserInfo;

/**
 * 登陆界面
 * 
 * @author WSXL
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	private ImageView user_head;
	private TextView user_slogans;
	private Spinner auto_user;
	private Button login;
	private Button logout;
	private ImageView adduser;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		user_head = (ImageView) findViewById(R.id.user_head);
		user_slogans = (TextView) findViewById(R.id.user_slogans);
		auto_user = (Spinner) findViewById(R.id.auto_user);
		login = (Button) findViewById(R.id.login);
		logout = (Button) findViewById(R.id.logout);
		adduser = (ImageView) findViewById(R.id.adduser);

		login.setOnClickListener(this);
		logout.setOnClickListener(this);
		adduser.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			startActivity(new Intent(LoginActivity.this, HomeActivity.class));
			break;

		case R.id.logout:
			finish();
			System.exit(0);
			break;

		case R.id.adduser:
			startActivity(new Intent(this, OAuthActivity.class));
			finish();
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		UserDao dao = new UserDao(this);
		final List<UserInfo> userData = dao.findAllUser();
		if (userData.isEmpty()) {
			Intent intent = new Intent(LoginActivity.this, OAuthActivity.class);
			startActivity(intent);
			finish();
		} else {
			/**
			 * 多用户的时候
			 */
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
			for (UserInfo u : userData) {
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("login_name", u.getUser_name());
				userList.add(userMap);
			}
			auto_user.setAdapter(new SimpleAdapter(this, userList,
					R.layout.login_user_item, new String[] { "login_name" },
					new int[] { R.id.longin_user_name}));
			auto_user.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					UserInfo u = userData.get(position);
					user_head.setImageDrawable(u.getUser_head());
					user_slogans.setText(u.getDescription());
					UserSession.nowUser = u;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
			});
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
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
